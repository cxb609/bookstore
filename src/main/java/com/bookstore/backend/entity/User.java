package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class User {
    private String user_id;
    private String name;
    private String password;
}
