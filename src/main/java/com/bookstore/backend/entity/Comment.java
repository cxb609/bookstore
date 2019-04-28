package com.bookstore.backend.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private String user_id;
    private String book_id;
    private Date date;
    private String comment;
}
