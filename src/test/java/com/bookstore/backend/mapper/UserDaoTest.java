package com.bookstore.backend.mapper;

import com.bookstore.backend.dao.UserDao;
import com.bookstore.backend.entity.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void findAllUsersTest(){
        List<User> userList = userDao.findAllUsers();
        for (User user:userList) {
            System.out.println(user);
        }
    }

    @Test
    public void getUserByIdTest(){
        User user1 = new User();
        user1.setUser_id("1234567890");
        user1.setName("大饼");
        user1.setPassword("123456");
        userDao.addUser(user1);
        User user2 = userDao.getUserById("1234567890");
        Assert.assertEquals(user1, user2);
        userDao.deleteUser("1234567890");
    }

    @Test
    public void getUserByNameTest(){
        User user1 = new User();
        user1.setUser_id("1234567891");
        user1.setName("大兵");
        user1.setPassword("123456");
        userDao.addUser(user1);
        User user2 = userDao.getUserByName("大兵");
        Assert.assertEquals(user1, user2);
        userDao.deleteUser("1234567891");
    }

    @Test
    public void addUserTest(){
        User user1 = new User();
        user1.setUser_id("1234567892");
        user1.setName("小兵");
        user1.setPassword("123456");
        userDao.addUser(user1);
        User user2 = userDao.getUserById("1234567892");
        Assert.assertEquals(user1, user2);
        userDao.deleteUser("1234567892");
    }

    @Test
    public void deleteUserTest(){
        List<User> userList1 = userDao.findAllUsers();
        User user = new User();
        user.setUser_id("1234567893");
        user.setName("小病");
        user.setPassword("123456");
        userDao.addUser(user);
        userDao.deleteUser("1234567893");
        List<User> userList2 = userDao.findAllUsers();
        Assert.assertEquals(userList1, userList2);
    }

    @Test
    public void modifyNameTest(){
        User user1 = new User();
        user1.setUser_id("1234567894");
        user1.setName("小冰");
        user1.setPassword("123456");
        userDao.addUser(user1);
        userDao.modifyName("1234567894", "小滨");
        User user2 = userDao.getUserById("1234567894");
        user1.setName("小滨");
        Assert.assertEquals(user1, user2);
        userDao.deleteUser("1234567894");
    }

    @Test
    public void modifyPasswordTest(){
        User user1 = new User();
        user1.setUser_id("1234567895");
        user1.setName("晓滨");
        user1.setPassword("123456");
        userDao.addUser(user1);
        userDao.modifyPassword("1234567895", "zxc123");
        User user2 = userDao.getUserById("1234567895");
        user1.setPassword("zxc123");
        Assert.assertEquals(user1, user2);
        userDao.deleteUser("1234567895");
    }
}