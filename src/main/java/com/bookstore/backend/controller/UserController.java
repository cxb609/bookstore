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
    public Result changePwd(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.modifyPassword(user_id, oldPassword, newPassword);
    }

    /**
     * 删除用户
     *
     *
     */
    @RequestMapping(value="/users", method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteUser(@RequestParam("user_id") String user_id){
        return userService.deleteUser(user_id);
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
    public  Result produceOrders(@RequestParam("orderLists") List<Map<String,Object>> orderLists) {
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
     * @param address
     * @return
     */
    @RequestMapping(value="/address", method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteAddress(@RequestParam("address") String address){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.deleteAddress(user_id,address);
    }

    /**
     * 添加地址
     * @param address
     * @return
     */
    @RequestMapping(value="/address", method=PUT, produces = "application/json;charset=UTF-8")
    public Result addAddress(@RequestParam("address") String address, @RequestParam("telephone") long telephone, @RequestParam("name") String name){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return userService.addAddress(user_id, address, telephone, name);
    }
}
