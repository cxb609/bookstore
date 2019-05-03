package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Book;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static org.springframework.web.bind.annotation.RequestMethod.*;

//商品页
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 添加图书
     */
    @RequestMapping(value = "/books", method = PUT)
    public Result addNewBook(@RequestBody Book book) {
        return bookService.postBook(book.getBook_id(), book.getTitle(), book.getPublisher(), book.getLanguage(),
                book.getIsbn(), book.getSize(), book.getWeight(), book.getBrand(), book.getCategory(), book.getPrice(),
                book.getPicture(), book.getStock(), book.getSale(), book.getDescription());
    }

    /**
     * 删除图书
     */
    @RequestMapping(value = "/books/{book_id}", method = DELETE)
    public Result deleteBook(@PathVariable("book_id") String book_id) {
        return bookService.deleteBook(book_id);
    }

    /**
     * 按ID查书
     */
    @RequestMapping(value = "/books/{book_id}", method = GET, produces = "application/json;charset=UTF-8")
    public Result getBookInfoById(@PathVariable("book_id") String book_id,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        return bookService.getBookBaseInfo(book_id, page);
    }

    /**
     * 修改图书信息
     */
    @RequestMapping(value = "/books/{book_id}", method = PUT)
    public Result changeBookInfo(@PathVariable("book_id") String book_id, @RequestBody Book book) {
        return bookService.updateBook(book.getBook_id(), book.getTitle(), book.getPublisher(), book.getLanguage(),
                book.getIsbn(), book.getSize(), book.getWeight(), book.getBrand(), book.getCategory(), book.getPrice(),
                book.getPicture(), book.getStock(), book.getSale(), book.getDescription());
    }

    /**待测
     * 添加图书评论
     */
    @RequestMapping(value = "/books/{book_id}/comments", method = POST, produces = "application/json;charset=UTF-8")
    public Result setBookComments(@PathVariable("book_id") String book_id,
                                  @RequestBody Comment comment) {
        return bookService.postComments(comment.getUser_id(), book_id, comment.getComment());
    }
    /*
    /**
     * 获取图书评论

    @RequestMapping(value = "/books/{book_id}/comments", method = GET, produces = "application/json;charset=UTF-8")
    public Result getBookComments(@PathVariable("book_id") String book_id,
                                  @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
                                  @RequestBody Comment comment) {
        return bookService.postComments(comment.getUser_id(), book_id, comment.getComment());
    }*/
}