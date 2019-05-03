package com.bookstore.backend.mapper;

import com.bookstore.backend.dao.ShoppingCartDao;
import com.bookstore.backend.dao.UserDao;
import com.bookstore.backend.entity.ShoppingCart;
import com.bookstore.backend.entity.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Test
    public void getShoppingCartTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUser_id("U123456789");
        shoppingCart1.setBook_id("B00112Y93U");
        shoppingCart1.setQuantity(10);
        shoppingCartDao.addShoppingCart(shoppingCart1);
        ShoppingCart shoppingCart2 = shoppingCartDao.getShoppingCart("U123456789", "B00112Y93U");
        Assert.assertEquals(shoppingCart1, shoppingCart2);
        shoppingCartDao.deleteShoppingCartsByUserId("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void getShoppingCartsByUserIdTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<ShoppingCart> shoppingCarts1 = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser_id("U123456789");
        shoppingCart.setBook_id("B00112Y93U");
        shoppingCart.setQuantity(10);
        shoppingCarts1.add(shoppingCart.clone());
        shoppingCart.setBook_id("B001130JN8");
        shoppingCart.setQuantity(5);
        shoppingCarts1.add(shoppingCart);
        shoppingCartDao.addShoppingCarts(shoppingCarts1);
        List<ShoppingCart> shoppingCarts2 = shoppingCartDao.getShoppingCartsByUserId("U123456789");
        Assert.assertEquals(shoppingCarts1, shoppingCarts2);
        shoppingCartDao.deleteShoppingCartsByUserId("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void addShoppingCartTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUser_id("U123456789");
        shoppingCart1.setBook_id("B00112Y93U");
        shoppingCart1.setQuantity(10);
        shoppingCartDao.addShoppingCart(shoppingCart1);
        ShoppingCart shoppingCart2 = shoppingCartDao.getShoppingCart("U123456789", "B00112Y93U");
        Assert.assertEquals(shoppingCart1, shoppingCart2);
        shoppingCartDao.deleteShoppingCartsByUserId("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void addShoppingCartsTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<ShoppingCart> shoppingCarts1 = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser_id("U123456789");
        shoppingCart.setBook_id("B00112Y93U");
        shoppingCart.setQuantity(10);
        shoppingCarts1.add(shoppingCart.clone());
        shoppingCart.setBook_id("B001130JN8");
        shoppingCart.setQuantity(5);
        shoppingCarts1.add(shoppingCart);
        shoppingCartDao.addShoppingCarts(shoppingCarts1);
        List<ShoppingCart> shoppingCarts2 = shoppingCartDao.getShoppingCartsByUserId("U123456789");
        Assert.assertEquals(shoppingCarts1, shoppingCarts2);
        shoppingCartDao.deleteShoppingCartsByUserId("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void deleteShoppingCartTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUser_id("U123456789");
        shoppingCart1.setBook_id("B00112Y93U");
        shoppingCart1.setQuantity(10);
        shoppingCartDao.addShoppingCart(shoppingCart1);
        shoppingCartDao.deleteShoppingCart("U123456789", "B00112Y93U");
        ShoppingCart shoppingCart2 = shoppingCartDao.getShoppingCart("U123456789", "B00112Y93U");
        Assert.assertNull(shoppingCart2);
        userDao.deleteUser("U123456789");
    }

    @Test
    public void deleteShoppingCartsByUserIdTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<ShoppingCart> shoppingCarts1 = new ArrayList<>();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser_id("U123456789");
        shoppingCart.setBook_id("B00112Y93U");
        shoppingCart.setQuantity(10);
        shoppingCarts1.add(shoppingCart.clone());
        shoppingCart.setBook_id("B001130JN8");
        shoppingCart.setQuantity(5);
        shoppingCarts1.add(shoppingCart);
        shoppingCartDao.addShoppingCarts(shoppingCarts1);
        shoppingCartDao.deleteShoppingCartsByUserId("U123456789");
        List<ShoppingCart> shoppingCarts2 = new ArrayList<>();
        Assert.assertEquals(shoppingCarts2, shoppingCartDao.getShoppingCartsByUserId("U123456789"));
        userDao.deleteUser("U123456789");
    }

    @Test
    public void modifyQuantityTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUser_id("U123456789");
        shoppingCart1.setBook_id("B00112Y93U");
        shoppingCart1.setQuantity(10);;
        shoppingCartDao.addShoppingCart(shoppingCart1);
        shoppingCartDao.modifyQuantity("U123456789", "B00112Y93U", 5);
        shoppingCart1.setQuantity(5);
        ShoppingCart shoppingCart2 = shoppingCartDao.getShoppingCart("U123456789", "B00112Y93U");
        Assert.assertEquals(shoppingCart1, shoppingCart2);
        shoppingCartDao.deleteShoppingCartsByUserId("U123456789");
        userDao.deleteUser("U123456789");
    }
}