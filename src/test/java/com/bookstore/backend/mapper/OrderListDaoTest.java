package com.bookstore.backend.mapper;

import com.bookstore.backend.dao.OrderListDao;
import com.bookstore.backend.entity.OrderList;

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
    private OrderListDao orderListDao;

//    @Test
//    public void getOrdersByUserIdTest(){
//        List<OrderList> orderList1 = new ArrayList<OrderList>();
//        OrderList order = new OrderList();
//        order.setOrder_id("ASD123456");
//        order.setBook_id("zxc1234567");
//        order.setUser_id("1234567890");
//        order.setDate();
//        order.setQuantity(10);
//        orderList1.add(order);
//        order.setOrder_id("ASD123456");
//        order.setBook_id("vbn1234567");
//        order.setUser_id("1234567890");
//        order.setDate();
//        order.setQuantity(5);
//        orderList1.add(order);
//        orderListDao.addOrder(orderList1.get(0));
//        orderListDao.addOrder(orderList1.get(1));
//        List<OrderList> orderList2 = orderListDao.getOrdersByUserId("1234567890");
//        Assert.assertEquals(orderList1, orderList2);
//        orderListDao.deleteOrdersByOrderId("ASD123456");
//    }

}