package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class ShoppingCart {
    private String user_id;
    private String book_id;
    private int quantity;
}
