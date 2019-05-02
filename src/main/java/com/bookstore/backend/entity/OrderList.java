package com.bookstore.backend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderList {
    private String order_id;
    private String book_id;
    private String user_id;
    private long date;
    private int quantity;
}
