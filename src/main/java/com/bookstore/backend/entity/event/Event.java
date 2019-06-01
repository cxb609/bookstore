package com.bookstore.backend.entity.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Event {
    private final String msg;
    private static Logger logger = LoggerFactory.getLogger(Event.class);

    public Event(String msg){
        this.msg = msg;
        logger.info(msg);
    }

    public String toString(){
        return msg;
    }
}
