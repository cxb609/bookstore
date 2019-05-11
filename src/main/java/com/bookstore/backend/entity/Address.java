package com.bookstore.backend.entity;

import lombok.Data;

@Data
public class Address implements Cloneable{
    private String user_id;
    private String address;
    private long telephone;
    private String name;

    @Override
    public Address clone() {
        Address o = null;
        try
        {
            o = (Address) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return o;
    }
}
