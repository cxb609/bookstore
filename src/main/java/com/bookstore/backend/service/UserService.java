package com.bookstore.backend.service;

import com.bookstore.backend.dao.UserDao;
import com.bookstore.backend.entity.ErrorCode;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.entity.ServiceException;
import com.bookstore.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    /**
     * 登录
     * @param name
     * @param password
     * @return
     */
    public Result login(String name, String password){
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

    public Result register(String name, String password){
        if(userDao.getUserByName(name) != null){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"用户名已存在");
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        //Integer affectedRow = userDao.addUser(user);
        return Result.OK(null).build();
    }

}
