package com.bookstore.backend.controller;

import com.bookstore.backend.entity.User;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.service.BookService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static org.springframework.web.bind.annotation.RequestMethod.*;
@RestController
public class UserController {
    @Autowired
    private BookService bookService;
    /**
     * 登录
     *
     *
     */
    @RequestMapping(value="/users", method=POST, produces = "application/json;charset=UTF-8")
    public Result login(@RequestBody User user){
        //todo
        return null;
    }
    /**
     * 注册
     *
     *
     */
    @RequestMapping(value="/register", method=POST, produces = "application/json;charset=UTF-8")
    public Result register(@RequestBody User user){
        //todo
        return null;
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
