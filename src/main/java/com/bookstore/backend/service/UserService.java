package com.bookstore.backend.service;

import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.dao.OrderListDao;
import com.bookstore.backend.dao.UserDao;
import com.bookstore.backend.entity.*;
import com.bookstore.backend.util.OrderIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
        return Result.OK(userInDb.getUser_id()).build();
    }

    /**
     * 注册
     * @param name
     * @param password
     * @return
     */
    public Result register(String name, String password){
        if(userDao.getUserByName(name) != null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"用户名已存在");
        }
        User user = new User();
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
     * @param name
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public Result modifyPassword(String name, String oldPassword, String newPassword){
        User user = userDao.getUserByName(name);
        if(user == null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"用户不存在");
        }else if(user.getPassword() != oldPassword){
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
    public Result getOrderList(){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if(user_id == null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"当前处于未登录状态，请登录");
        }
        List<OrderList> orderLists = orderListDao.getOrdersByUserId(user_id);
        return Result.OK(orderLists).build();
    }

    /**
     * 下单
     * @param orderLists
     * @return
     */
    @Transactional
    public Result addOrder(List<OrderList> orderLists){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if(user_id == null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"当前处于未登录状态，请登录");
        }
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
        }
        Integer affectedRow = orderListDao.addOrders(orderLists);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"添加订单失败，请重试");
        }
        return Result.OK("添加订单成功").build();
    }
}
