package com.bookstore.backend.service;

import com.bookstore.backend.entity.OrderList;
import com.bookstore.backend.entity.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void buyTest(){
        List<OrderList> orderLists = new LinkedList<>();
        OrderList orderList1 = new OrderList();
        orderList1.setOrder_id("O4565");
        orderList1.setBook_id("B00112Y93U");
        orderList1.setUser_id("U0001");
        orderList1.setDate(System.currentTimeMillis());
        orderList1.setQuantity(1);
        OrderList orderList2 = new OrderList();
        orderList2.setOrder_id("O4565");
        orderList2.setBook_id("B001130JN8");
        orderList2.setUser_id("U0001");
        orderList2.setDate(System.currentTimeMillis());
        orderList2.setQuantity(1);
        orderLists.add(orderList1);
        orderLists.add(orderList2);
        Result result = userService.addOrder(orderLists);
        System.out.println(result.getData());
    }
}