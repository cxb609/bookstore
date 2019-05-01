package com.bookstore.backend.dao;

import com.bookstore.backend.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    /**
     * 查找所有用户
     * @return
     */
    List<User> findAllUsers();

    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    User getUserById(String userId);

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据用户ID删除用户
     * @param userId
     */
    void deleteUser(String userId);

    /**
     * 修改用户名
     * @param userId
     * @param newName
     */
    void modifyName(@Param("userId") String userId, @Param("newName") String newName);

    /**
     * 修改密码
     * @param userId
     * @param newPwd
     */
    void modifyPassword(@Param("userId") String userId, @Param("newPwd") String newPwd);
}
