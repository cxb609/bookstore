package com.bookstore.backend.mapper;
;
import com.bookstore.backend.dao.OrderListDao;
import com.bookstore.backend.dao.UserDao;
import com.bookstore.backend.entity.OrderList;
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
public class OrderListDaoTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderListDao orderListDao;

    @Test
    public void getOrdersByUserIdTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<OrderList> orderList1 = new ArrayList<OrderList>();
        OrderList order = new OrderList();
        long currTime = System.currentTimeMillis();
        order.setOrder_id("o123456");
        order.setBook_id("B00112Y93U");
        order.setUser_id("U123456789");
        order.setDate(currTime);
        order.setQuantity(10);
        orderList1.add(order.clone());
        order.setBook_id("B001130JN8");
        order.setQuantity(5);
        orderList1.add(order);
        orderListDao.addOrder(orderList1.get(0));
        orderListDao.addOrder(orderList1.get(1));
        List<OrderList> orderList2 = orderListDao.getOrdersByUserId("U123456789");
        Assert.assertEquals(orderList1, orderList2);
        orderListDao.deleteOrdersByOrderId("o123456");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void getOrdersByUserIdAndDateTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<OrderList> orderList1 = new ArrayList<OrderList>();
        OrderList order = new OrderList();
        long currTime = System.currentTimeMillis();
        order.setOrder_id("o123456");
        order.setBook_id("B00112Y93U");
        order.setUser_id("U123456789");
        order.setDate(currTime);
        order.setQuantity(10);
        orderListDao.addOrder(order.clone());
        order.setBook_id("B001130JN8");
        order.setDate(currTime + 1000);
        order.setQuantity(5);
        orderList1.add(order);
        orderListDao.addOrder(order);
        List<OrderList> orderList2 = orderListDao.getOrdersByUserIdAndDate("U123456789", currTime + 1, currTime + 1000);
        Assert.assertEquals(orderList1, orderList2);
        orderListDao.deleteOrdersByOrderId("o123456");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void getOrdersByOrderIdTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<OrderList> orderList1 = new ArrayList<OrderList>();
        OrderList order = new OrderList();
        long currTime = System.currentTimeMillis();
        order.setOrder_id("o123456");
        order.setBook_id("B00112Y93U");
        order.setUser_id("U123456789");
        order.setDate(currTime);
        order.setQuantity(10);
        orderList1.add(order.clone());
        order.setBook_id("B001130JN8");
        order.setQuantity(5);
        orderList1.add(order);
        orderListDao.addOrder(orderList1.get(0));
        orderListDao.addOrder(orderList1.get(1));
        List<OrderList> orderList2 = orderListDao.getOrdersByOrderId("o123456");
        Assert.assertEquals(orderList1, orderList2);
        orderListDao.deleteOrdersByOrderId("o123456");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void addOrderTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        OrderList order1 = new OrderList();
        long currTime = System.currentTimeMillis();
        order1.setOrder_id("o123456");
        order1.setBook_id("B00112Y93U");
        order1.setUser_id("U123456789");
        order1.setDate(currTime);
        order1.setQuantity(10);;
        orderListDao.addOrder(order1);
        OrderList order2 = orderListDao.getOrdersByUserId("U123456789").get(0);
        Assert.assertEquals(order1, order2);
        orderListDao.deleteOrdersByOrderId("o123456");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void addOrdersTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<OrderList> orderList1 = new ArrayList<OrderList>();
        OrderList order = new OrderList();
        long currTime = System.currentTimeMillis();
        order.setOrder_id("o123456");
        order.setBook_id("B00112Y93U");
        order.setUser_id("U123456789");
        order.setDate(currTime);
        order.setQuantity(10);
        orderList1.add(order.clone());
        order.setBook_id("B001130JN8");
        order.setQuantity(5);
        orderList1.add(order);
        orderListDao.addOrders(orderList1);
        List<OrderList> orderList2 = orderListDao.getOrdersByUserId("U123456789");
        Assert.assertEquals(orderList1, orderList2);
        orderListDao.deleteOrdersByOrderId("o123456");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void deleteOrderTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        OrderList order = new OrderList();
        long currTime = System.currentTimeMillis();
        order.setOrder_id("o123456");
        order.setBook_id("B00112Y93U");
        order.setUser_id("U123456789");
        order.setDate(currTime);
        order.setQuantity(10);;
        orderListDao.addOrder(order);
        orderListDao.deleteOrder("o123456", "B00112Y93U");
        List<OrderList> nullList = new ArrayList<OrderList>();
        Assert.assertEquals(nullList, orderListDao.getOrdersByOrderId("o123456"));
        userDao.deleteUser("U123456789");
    }

    @Test
    public void deleteOrdersByOrderIdTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<OrderList> orderList = new ArrayList<OrderList>();
        OrderList order = new OrderList();
        long currTime = System.currentTimeMillis();
        order.setOrder_id("o123456");
        order.setBook_id("B00112Y93U");
        order.setUser_id("U123456789");
        order.setDate(currTime);
        order.setQuantity(10);
        orderList.add(order.clone());
        order.setBook_id("B001130JN8");
        order.setQuantity(5);
        orderList.add(order);
        orderListDao.addOrders(orderList);
        orderListDao.deleteOrdersByOrderId("o123456");
        List<OrderList> nullList = new ArrayList<OrderList>();
        Assert.assertEquals(nullList, orderListDao.getOrdersByOrderId("o123456"));
        userDao.deleteUser("U123456789");
    }

    @Test
    public void modifyQuantityTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        OrderList order1 = new OrderList();
        long currTime = System.currentTimeMillis();
        order1.setOrder_id("o123456");
        order1.setBook_id("B00112Y93U");
        order1.setUser_id("U123456789");
        order1.setDate(currTime);
        order1.setQuantity(10);;
        orderListDao.addOrder(order1);
        orderListDao.modifyQuantity("o123456", "B00112Y93U", 5);
        order1.setQuantity(5);
        OrderList order2 = orderListDao.getOrdersByUserId("U123456789").get(0);
        Assert.assertEquals(order1, order2);
        orderListDao.deleteOrdersByOrderId("o123456");
        userDao.deleteUser("U123456789");
    }
}