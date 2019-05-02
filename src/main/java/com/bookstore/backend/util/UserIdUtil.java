package com.bookstore.backend.util;

/**
 * 构造用户id
 */
public class UserIdUtil {

    public static String produceUserId(){
        long dateStamp = System.currentTimeMillis();
        return "U" + dateStamp%10000000 + (int)(Math.random()*10);
    }
//
//    public static void main(String[] args){
//        System.out.println(produceUserId());
//    }
}
