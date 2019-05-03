package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class ShoppingCart implements Cloneable{
    private String user_id;
    private String book_id;
    private int quantity;

    @Override
    public ShoppingCart clone() {
        ShoppingCart o = null;
        try
        {
            o = (ShoppingCart) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return o;
    }
}
