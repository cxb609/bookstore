package com.bookstore.backend.service;

import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.entity.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    /**
     * 查找全部图书
     * @param start
     * @return
     */
    public Result getAllBook(int start){
        PageHelper.startPage(start,12);
        List<ShowBook> bookList = bookDao.findAllBook();
        if(bookList == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书信息获取失败");
        }
        PageInfo<ShowBook> page = new PageInfo<>(bookList);
        Map<String, Object> resultData = new LinkedHashMap<>();
        resultData.put("totalNum",page.getTotal());
        resultData.put("data",page.getList());
        return Result.OK(resultData).build();
    }
    /**
     * 获取首页推荐图书
     * @return
     */
    public Result getHomeBooksInfo(){
        List<ShowBook> bookData = bookDao.getHomeBook();
        if(bookData == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION, "图书信息获取失败");
        }
        return Result.OK(bookData).build();
    }

    /**
     * 查找某一类书
     * @param category 类别
     * @param start 页数
     * @param pageSize 一页图书数目
     * @return
     */
    public Result getBookInfoByCategory(String category, int start, int pageSize){
        PageHelper.startPage(start,pageSize);
        List<ShowBook> bookList = bookDao.findBookByCategory(category);
        if(bookList == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书信息获取失败");
        }
        PageInfo<ShowBook> page = new PageInfo<>(bookList);
        Map<String, Object> resultData = new LinkedHashMap<>();
        resultData.put("totalNum",page.getTotal());
        resultData.put("data",page.getList());
        return Result.OK(resultData).build();
    }

    /**
     * 根据关键字查询图书
     * @param keyword
     * @param start
     * @param pageSize
     * @return
     */
    public Result getBookInfoByKeyword(String keyword, int start, int pageSize){
        PageHelper.startPage(start,pageSize);
        List<ShowBook> bookList = bookDao.findBookByKey(keyword);
        if(bookList == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书信息获取失败");
        }
        PageInfo<ShowBook> page = new PageInfo<>(bookList);
        Map<String, Object> resultData = new LinkedHashMap<>();
        resultData.put("totalNum",page.getTotal());
        resultData.put("data",page.getList());
        return Result.OK(resultData).build();
    }

    /**
     * 根据id查找书的详情
     * @param book_id
     * @return
     */
    public Result getBookBaseInfo(String book_id){
        Book book = bookDao.getBookById(book_id);
        if(book == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书详情信息获取失败");
        }
        Map<String,Object> resultData = new LinkedHashMap<>();
        resultData.put("data",book);
        List<Comment> commentList = bookDao.getBookCommentsById(book_id);
        resultData.put("comments",commentList);
        return Result.OK(resultData).build();
    }

    /**
     * 添加评论
     * @param user_id
     * @param book_id
     * @param commentMsg
     * @return
     */
    public Result postComments(String user_id, String book_id, String commentMsg){
        Comment comment = new Comment();
        comment.setUser_id(user_id);
        comment.setBook_id(book_id);
        comment.setDate(System.currentTimeMillis());
        comment.setComment(commentMsg);
        Integer affectedRow = bookDao.insertBookComment(comment);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"添加评论失败");
        }
        return Result.OK("添加评论成功").build();
    }
}
