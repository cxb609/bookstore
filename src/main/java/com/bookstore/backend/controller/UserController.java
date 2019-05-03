package com.bookstore.backend.controller;

import com.bookstore.backend.entity.ErrorCode;
import com.bookstore.backend.entity.ServiceException;
import com.bookstore.backend.entity.User;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.entity.OrderList;
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
    public Result login(@RequestBody User user){
        return userService.login(user.getName(),user.getPassword());
    }
    /**
     * 注册
     *
     *
     */
    @RequestMapping(value="/register", method=POST, produces = "application/json;charset=UTF-8")
    public Result register(@RequestBody User user){

        return userService.register(user.getName(),user.getPassword());

    }
    /**
     * 修改密码
     *
     *
     */
    @RequestMapping(value="/users", method=PUT, produces = "application/json;charset=UTF-8")
    public Result changePwd(@RequestBody Map<String, Object> map){
        return userService.modifyPassword((String)map.get("user_id"), (String)map.get("oldPassword"),(String)map.get("newPassword"));
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
}
