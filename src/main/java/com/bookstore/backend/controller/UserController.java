package com.bookstore.backend.controller;

import com.bookstore.backend.entity.User;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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
        //System.out.print(user.getUser_id()+user.getName()+user.getPassword());
        return userService.register(user.getName(),user.getPassword());
    }
    /**
     * 修改密码
     *
     *
     */
    @RequestMapping(value="/users/{user_id}", method=PUT, produces = "application/json;charset=UTF-8")
    public Result changePwd(@PathVariable("user_id") String user_id){
        //todo
        return null;
    }

    /**
     * 删除用户
     *
     *
     */
    @RequestMapping(value="/users/{user_id}", method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteUser(@PathVariable("user_id") String user_id){
        //todo
        return null;
    }
}
