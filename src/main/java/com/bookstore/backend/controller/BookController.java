package com.bookstore.backend.controller;

import com.bookstore.backend.entity.*;
import com.bookstore.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;

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
    public Result addNewBook(@RequestParam("book_id") String book_id, @RequestParam("title") String title, @RequestParam("author") String author,
                             @RequestParam("publisher") String publisher, @RequestParam("language") String language, @RequestParam("isbn") String isbn,
                             @RequestParam("size") String size, @RequestParam("weight") String weight, @RequestParam("brand") String brand,
                             @RequestParam("category") String category, @RequestParam(value = "price", required = false, defaultValue = "0") double price,
                             @RequestParam("picture") String picture, @RequestParam(value = "stock", required = false, defaultValue = "0") int stock,
                             @RequestParam(value = "sale", required = false,defaultValue = "0") int sale,
                             @RequestParam(value = "description", required = false, defaultValue = "") String description) {
        return bookService.postBook(book_id, title, author, publisher, language, isbn, size, weight, brand, category, price, picture, stock, sale, description);
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
    public Result getBookInfoById(@PathVariable("book_id") String book_id) {
        return bookService.getBookBaseInfo(book_id);
    }

    /**
     * 分页获取图书评论
     * @param book_id
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/books/{book_id}/comments", method = GET, produces = "application/json;charset=UTF-8")
    public Result getBookComments(@PathVariable("book_id") String book_id,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize){
        return bookService.getBookComments(book_id,page,pageSize);
    }

    /**
     * 修改图书信息
     */
    @RequestMapping(value = "/books/{book_id}", method = PUT)
    public Result changeBookInfo(@PathVariable("book_id") String book_id, @RequestParam("title") String title, @RequestParam("author") String author,
                                 @RequestParam("publisher") String publisher, @RequestParam("language") String language, @RequestParam("isbn") String isbn,
                                 @RequestParam("size") String size, @RequestParam("weight") String weight, @RequestParam("brand") String brand,
                                 @RequestParam("category") String category, @RequestParam(value = "price", required = false, defaultValue = "0") double price,
                                 @RequestParam("picture") String picture, @RequestParam(value = "stock", required = false, defaultValue = "0") int stock,
                                 @RequestParam(value = "sale", required = false,defaultValue = "0") int sale,
                                 @RequestParam(value = "description", required = false, defaultValue = "") String description) {
        return bookService.updateBook(book_id, title, author, publisher, language, isbn, size, weight, brand, category, price, picture, stock, sale, description);
    }

    /**待测
     * 添加图书评论
     */
    @RequestMapping(value = "/books/{book_id}/comments", method = POST, produces = "application/json;charset=UTF-8")
    public Result setBookComments(@PathVariable("book_id") String book_id,
                                  @RequestParam("comment") String comment) {
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return bookService.postComments(user_id, book_id, comment);
    }

}