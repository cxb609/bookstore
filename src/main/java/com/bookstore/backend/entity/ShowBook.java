package com.bookstore.backend.entity;

import lombok.Data;

/**
 * 书本的部分信息，用于在图书展示时(Book为图书详情页所展示)
 */
@Data
public class ShowBook {
    private String book_id;
    private String title;
    private String author;
    private String category;
    private double price;
    private String picture;
}
