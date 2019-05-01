package com.bookstore.backend.dao;

import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.entity.Book;

import java.util.Date;
import java.util.List;

public interface BookDao {

    /**
     * 查找所有图书
     * @return
     */
    List<Book> findAllBook();

    /**
     * 根据关键字模糊查询
     * @param key 搜索框内输入的关键字
     * @return
     */
    List<Book> findBookByKey(String key);

    /**
     * 查找某一类书
     * @param category 类别
     * @return
     */
    List<Book> findBookByCategory(String category);

    /**
     * 根据书id查找图书评论
     * @param book_id
     * @return
     */
    List<Comment> getBookCommentsById(String book_id);

    /**
     * 插入书评
     * @param comment
     */
    void insertBookComment(Comment comment);

    /**
     * 删除图书
     * @param book_id
     */
    void deleteBookById(String book_id);

    /**
     * 添加图书
     * @param book
     */
    void addBook(Book book);

    /**
     * 库存stock-1 销量sale+1
     * @param book_id
     */
    void updateBookSS(String book_id);


}
