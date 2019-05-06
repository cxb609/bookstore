package com.bookstore.backend.service;

import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.dao.OrderListDao;
import com.bookstore.backend.entity.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderListDao orderListDao;

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
     * 获取全部分类
     * @return
     */
    public Result getCategories(){
        List<String> categories = bookDao.getCategory();
        if(categories == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书分类获取失败");
        }
        return Result.OK(categories).build();
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
     * @param commentPage
     * @return
     */
    public Result getBookBaseInfo(String book_id){
        Book book = bookDao.getBookById(book_id);
        if(book == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书详情信息获取失败");
        }
        return Result.OK(book).build();
    }

    /**
     * 获取图书评论
     * @param book_id
     * @param commentPage
     * @param pageSize
     * @return
     */
    public Result getBookComments(String book_id, int commentPage, int pageSize){
        Book book = bookDao.getBookById(book_id);
        if(book == null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"图书不存在");
        }
        PageHelper.startPage(commentPage, pageSize);
        List<Comment> commentList = bookDao.getBookCommentsById(book_id);
        PageInfo<Comment> page = new PageInfo<>(commentList);
        Map<String,Object> resultData = new LinkedHashMap<>();
        resultData.put("commentsNum",page.getTotal());
        resultData.put("comments",commentList);
        return Result.OK(resultData).build();
    }

    /**
     * 添加评论。错误返回代码有问题，无法判断是数据库问题还是用户输入问题，暂时都返回服务器问题。
     * @param user_id
     * @param book_id
     * @param commentMsg
     * @return
     */
    public Result postComments(String user_id, String book_id, String commentMsg){
        List<OrderList> orderLists = orderListDao.getOrdersByUserId(user_id);
        boolean isOrder = false;
        for(OrderList orderList:orderLists){
            if(orderList.getBook_id() == book_id){
                isOrder = true;
                break;
            }
        }
        if(!isOrder){
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON,"该用户无法评论此书");
        }
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

    /**
     * 删除图书
     * @param book_id
     * @return
     */
    public Result deleteBook(String book_id){
        Integer affectedRow = bookDao.deleteBookById(book_id);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"删除图书失败");
        }
        return Result.OK("删除图书成功").build();
    }

    /**
     * 添加图书。目前用不到
     * @param book_id
     * @param title
     * @param publisher
     * @param language
     * @param isbn
     * @param size
     * @param weight
     * @param brand
     * @param category
     * @param price
     * @param picture
     * @param stock
     * @param sale
     * @param description
     * @return
     */
    public Result postBook(String book_id, String title, String author, String publisher, String language, String isbn, String size,
                           String weight, String brand, String category, double price, String picture, int stock, int sale,
                           String description){
        Book book = new Book();
        book.setBook_id(book_id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setLanguage(language);
        book.setIsbn(isbn);
        book.setSize(size);
        book.setWeight(weight);
        book.setBrand(brand);
        book.setCategory(category);
        book.setPrice(price);
        book.setPicture(picture);
        book.setStock(stock);
        book.setSale(sale);
        book.setDescription(description);
        Integer affectedRow = bookDao.addBook(book);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"添加图书失败");
        }
        return Result.OK("添加图书成功").build();
    }

    /**
     * 更新图书。暂时用不到
     * @param book_id
     * @param title
     * @param publisher
     * @param language
     * @param isbn
     * @param size
     * @param weight
     * @param brand
     * @param category
     * @param price
     * @param picture
     * @param stock
     * @param sale
     * @param description
     * @return
     */
    public Result updateBook(String book_id, String title, String author, String publisher, String language, String isbn, String size,
                             String weight, String brand, String category, double price, String picture, int stock, int sale,
                             String description){
        bookDao.deleteBookById(book_id);
        Book book = new Book();
        book.setBook_id(book_id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setLanguage(language);
        book.setIsbn(isbn);
        book.setSize(size);
        book.setWeight(weight);
        book.setBrand(brand);
        book.setCategory(category);
        book.setPrice(price);
        book.setPicture(picture);
        book.setStock(stock);
        book.setSale(sale);
        book.setDescription(description);
        Integer affectedRow = bookDao.addBook(book);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"修改图书失败");
        }
        return Result.OK("修改图书成功").build();
    }
}
