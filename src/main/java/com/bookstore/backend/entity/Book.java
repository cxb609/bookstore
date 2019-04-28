package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class Book {
    private String book_id;
    private String title;
    private String author;
    private String publisher;
    private String language;
    private String isbn;
    private String size;
    private String weight;
    private String brand;
    private String category;
    private double price;
    private String picture;
    private int stock;
    private int sale;
    private String description;
}
