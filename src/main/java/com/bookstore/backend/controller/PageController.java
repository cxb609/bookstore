package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Result;
import com.bookstore.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PageController {
    @Autowired
    private BookService bookService;
    /**
     * 获取首页图书信息
     *
     *
     */
    @RequestMapping(value={"/","/home"}, method=GET, produces = "application/json;charset=UTF-8")
    public Result getHomeInfo(@RequestParam(value = "page",required = false,defaultValue = "0") int page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        return bookService.getHomeBooksInfo();
    }
    /**
     * 获取分类
     *
     *
     */
    @RequestMapping(value="/categories", method=GET, produces = "application/json;charset=UTF-8")
    public Result listBooks(){
        return bookService.getCategories();
    }
    /**
     * 获取分类页图书信息
     *
     *
     */
    @RequestMapping(value="/categories/{category}", method=GET, produces = "application/json;charset=UTF-8")
    public Result listBooks(@PathVariable("category") String category,
                            @RequestParam(value = "page",required = false,defaultValue = "0") int page,
                            @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        return bookService.getBookInfoByCategory(category,page,pageSize);
    }

    /**
     * 按关键字搜索
     *
     *
     */
    @RequestMapping(value="/search/{keyWords}", method=GET, produces = "application/json;charset=UTF-8")
    public Result searchByKey(@PathVariable("keyWords") String keyword,
                              @RequestParam(value = "page",required = false,defaultValue = "0") int page,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        return bookService.getBookInfoByKeyword(keyword, page, pageSize);
    }

}
