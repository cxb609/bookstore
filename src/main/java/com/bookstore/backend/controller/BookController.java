package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.service.BookService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static org.springframework.web.bind.annotation.RequestMethod.*;

//商品页
@RestController
public class BookController{
    @Autowired
    private BookService bookService;

    @Autowired
    private PageHelper pageHelper;
    /**
     * 添加图书
     *
     *
     */
    @RequestMapping(value="/books", method=PUT)
    public Result setNewBook(@RequestBody Book book){
        //todo
        return null;
    }

    /**
     * 删除图书
     *
     *
     */
    @RequestMapping(value="/books/{book_id}", method=DELETE)
    public Result setNewBook(@PathVariable("book_id") String book_id){
        //todo
        return null;
    }

    /**
     * 按ID查书
     *
     *
     */
//    @RequestMapping(value="/books/{book_id}", method=GET, produces = "application/json;charset=UTF-8")
//    public Result getBookInfoById(@PathVariable("book_id") String book_id,@RequestParam(value = "page",required = false,defaultValue = "1") int page,@RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
//        return bookService.getBookBaseInfo(book_id);
//
//    }

    /**
     * 修改图书信息
     *
     *
     */
    @RequestMapping(value="/books/{book_id}", method=PUT)
    public Result addBookInfo(@PathVariable("book_id") String book_id, @RequestBody Book book){
        //todo
        return null;
    }

    /**
     * 获取图书评论
     *
     *
     */
    @RequestMapping(value="/books/{book_id}/comments", method=POST, produces = "application/json;charset=UTF-8")
    public Result setBookComments(@PathVariable("book_id") String book_id,@RequestParam(value = "page",required = false,defaultValue = "1") int page,@RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize, @RequestBody Comment comment){
        return bookService.postComments(comment.getUser_id(),book_id,comment.getComment());
    }


}