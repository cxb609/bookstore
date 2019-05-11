package com.bookstore.backend.service;

import com.bookstore.backend.dao.*;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.util.OrderIdUtil;
import com.bookstore.backend.util.UserIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    OrderListDao orderListDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Autowired
    AddressDao addressDao;

    /**
     * 登录
     * @param name
     * @param password
     * @return
     */
    public Result login(String name, String password) {
        User userInDb = userDao.getUserByName(name);
        if(userInDb == null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"用户名不存在");
        }
        if(!password.equals(userInDb.getPassword())){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"密码错误");
        }
        //将用户id存入session，session包含用户id即表示处在登录状态。
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("user_id",userInDb.getUser_id());
//        System.out.println(request.getSession().getAttribute("user_id"));
        return Result.OK(userInDb.getUser_id()).build();
    }

    /**
     * 注册
     * @param name
     * @param password
     * @return
     */
    public Result register(String name, String password){
        System.out.println(userDao.getUserByName(name));
        if(userDao.getUserByName(name) != null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"用户名已存在");
        }
        User user = new User();
        user.setUser_id(UserIdUtil.produceUserId());
        user.setName(name);
        user.setPassword(password);
        Integer affectedRow = userDao.addUser(user);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"注册失败");
        }
        return Result.OK("注册成功").build();
    }

    /**
     *修改密码
     * @param user_id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public Result modifyPassword(String user_id, String oldPassword, String newPassword){
        User user = userDao.getUserById(user_id);
        if(user == null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"用户不存在");
        }else if(!oldPassword.equals(user.getPassword())){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "密码验证错误");
        }
        Integer affectedRow = userDao.modifyPassword(user.getUser_id(), newPassword);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"修改密码失败，请重试");
        }
        return Result.OK("修改密码成功").build();
    }

    /**
     * 删除用户，只适用于管理员操作
     * @param user_id
     * @return
     */
    public Result deleteUser(String user_id){
        Integer affectedRow = userDao.deleteUser(user_id);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"删除失败");
        }
        return Result.OK("删除用户 " + user_id + " 成功").build();
    }

    /**
     * 获取订单
     * @return
     */
    public Result getOrderList(String user_id){
        List<String> orderIdList = orderListDao.getOrderId(user_id);
        List<Map<String,Object>> resultData = new LinkedList<Map<String, Object>>();
        for(String orderId:orderIdList){
            Map<String,Object> orderMap = new HashMap<>();
            orderMap.put("order_id",orderId);
            List<OrderList> orderLists = orderListDao.getOrdersByOrderId(orderId);
            orderMap.put("date", orderLists.get(0).getDate());
            orderMap.put("total_price",orderListDao.getTotalPrice(orderId));
            orderMap.put("total_quantity", orderListDao.getTotalQuantity(orderId));
            List<Map<String,Object>> bookList = new LinkedList<>();
            for(OrderList orderList:orderLists){
                Map<String,Object> bookInfo = new HashMap<>();
                Book book = bookDao.getBookById(orderList.getBook_id());
                bookInfo.put("price",book.getPrice());
                bookInfo.put("title",book.getTitle());
                bookInfo.put("picture",book.getPicture());
                bookInfo.put("book_id",book.getBook_id());
                bookInfo.put("quantity",orderList.getQuantity());
                bookList.add(bookInfo);
            }
            orderMap.put("book_list",bookList);
            resultData.add(orderMap);
        }
        return Result.OK(resultData).build();
    }

    /**
     * 下单
     * 前提：用户已登陆
     * @param orderLists
     * @return
     */
    @Transactional
    public Result addOrder(String user_id,List<OrderList> orderLists){

        String orderId = OrderIdUtil.getOrderId();
        long dateStamp = System.currentTimeMillis();
        for (OrderList order:orderLists) {
            order.setOrder_id(orderId);
            order.setUser_id(user_id);
            order.setDate(dateStamp);
            int stock = bookDao.getBookById(order.getBook_id()).getStock();
            if(stock < order.getQuantity()){
                throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"库存不足,请重新选择");
            }
            bookDao.updateBookSS(order.getBook_id(), order.getQuantity());
            shoppingCartDao.deleteShoppingCart(user_id, order.getBook_id());
        }
        Integer affectedRow = orderListDao.addOrders(orderLists);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"添加订单失败，请重试");
        }
        return Result.OK("添加订单成功").build();
    }

    /**
     * 获取用户所有地址
     * @param user_id
     * @return
     */
    public Result getAddresses(String user_id){
        List<Address> addressList = addressDao.getAddresses(user_id);
        return Result.OK(addressList).build();
    }

    /**
     * 添加地址
     * @param address
     * @return
     */
    public Result addAddress(Address address){
        Address addr = new Address();
        Integer affectedRow = addressDao.addAddress(addr);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"添加地址失败");
        }
        return Result.OK("添加地址成功").build();
    }

    /**
     * 删除地址
     * @param user_id
     * @param address
     * @return
     */
    public Result deleteAddress(String user_id, String address){
        Integer affectedRow = addressDao.deleteAddress(user_id, address);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"删除地址失败");
        }
        return Result.OK("删除地址成功").build();
    }
}
