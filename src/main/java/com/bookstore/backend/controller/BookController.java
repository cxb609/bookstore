package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Book;
import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private PageHelper pageHelper;

    @GetMapping("/topbook")
    public Result getTopBook(){
        return bookService.getHomeBooksInfo();
    }

    @GetMapping("/{category}/{page}")
    public Result getBooksByCategory(@PathVariable("category") String category, @PathVariable("page") int page){
        return bookService.getBookInfoByCategory(category,page,10);
    }

}
