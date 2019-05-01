package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Book;
import com.bookstore.backend.dao.BookDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private PageHelper pageHelper;

    @GetMapping("/topbook")
    public List<Book> getTopBook(){
        PageHelper.startPage(0,10,"sale desc");
        List<Book> bookList = bookDao.findAllBook();
        PageInfo<Book> page = new PageInfo<>(bookList);
        List<Book> result = page.getList();
        return result;
    }
}
