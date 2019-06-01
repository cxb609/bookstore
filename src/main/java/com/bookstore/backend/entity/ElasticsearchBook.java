package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class ElasticsearchBook {
    private String title;
    private String description;
    private String book_id;

    public ElasticsearchBook(Book book){
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.book_id = book.getBook_id();
    }
}
