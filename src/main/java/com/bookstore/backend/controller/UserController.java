package com.bookstore.backend.controller;

import com.bookstore.backend.entity.*;
import com.bookstore.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping(value="/users", method=POST, produces = "application/json;charset=UTF-8")
    public Result login(@RequestParam("name") String name, @RequestParam("password") String password){
        return userService.login(name, password);
    }
    /**
     * 注册
     *
     *
     */
    @RequestMapping(value="/register", method=POST, produces = "application/json;charset=UTF-8")
    public Result register(@RequestParam("name") String name, @RequestParam("password") String password){
        return userService.register(name, password);
    }
    /**
     * 修改密码
     *
     *
     */
    @RequestMapping(value="/users", method=PUT, produces = "application/json;charset=UTF-8")
    public Result changePwd(@RequestBody Map<String, Object> map){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.modifyPassword(user_id, (String)map.get("oldPassword"),(String)map.get("newPassword"));
    }

    /**
     * 删除用户
     *
     *
     */
    @RequestMapping(value="/users", method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteUser(@RequestBody User user){
        return userService.deleteUser(user.getUser_id());
    }

    /**
     * 获取用户订单
     *
     * @param
     * @return
     */
    @RequestMapping(value="/orders", method=GET, produces = "application/json;charset=UTF-8")
    public  Result getOrders() {
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.getOrderList(user_id);
    }

    /**
     * 生成用户订单
     *
     * @param
     * @return
     */
    @RequestMapping(value="/orders", method=POST, produces = "application/json;charset=UTF-8")
    public  Result produceOrders(@RequestBody List<OrderList> orderLists) {
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.addOrder(user_id,orderLists);
    }

    /**
     * 获取所有地址
     * @return
     */
    @RequestMapping(value="/address", method=GET, produces = "application/json;charset=UTF-8")
    public Result getAddresses(){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.getAddresses(user_id);
    }

    /**
     * 删除地址
     * @param addr
     * @return
     */
    @RequestMapping(value="/address", method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteAddress(@RequestBody String addr){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.deleteAddress(user_id,addr);
    }

    /**
     * 添加地址
     * @param address
     * @return
     */
    @RequestMapping(value="/address", method=PUT, produces = "application/json;charset=UTF-8")
    public Result addAddress(@RequestBody Address address){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        address.setUser_id(user_id);
        return userService.addAddress(address);
    }
}
