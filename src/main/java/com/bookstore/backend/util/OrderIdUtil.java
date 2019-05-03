package com.bookstore.backend.util;


/**
 * 构造订单id
 */
public class OrderIdUtil {

    public static String getOrderId(){
        long dateStamp = System.currentTimeMillis();
        return "O" + dateStamp%10000000 + (int)(Math.random()*10);
    }
}