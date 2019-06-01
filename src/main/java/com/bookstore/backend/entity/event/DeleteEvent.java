package com.bookstore.backend.entity.event;

public class DeleteEvent extends Event{
    private final String book_id;

    public DeleteEvent(String book_id){
        super("delete a book, book_id: " + book_id);
        this.book_id = book_id;
    }

    public String getBook_id(){
        return book_id;
    }
}
