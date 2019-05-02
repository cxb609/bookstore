package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class OrderList implements Cloneable{
    private String order_id;
    private String book_id;
    private String user_id;
    private long date;
    private int quantity;

    @Override
    public OrderList clone() {
        OrderList o = null;
        try
        {
            o = (OrderList) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return o;
    }
}
