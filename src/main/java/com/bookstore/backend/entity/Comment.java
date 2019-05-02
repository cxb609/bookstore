package com.bookstore.backend.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private String user_id;
    private String book_id;
    private long date;
    private String comment;
}
