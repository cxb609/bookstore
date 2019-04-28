package com.scutj2ee.bookstore.mapper;

import com.scutj2ee.bookstore.entity.Book;
import com.scutj2ee.bookstore.entity.Comment;

import java.util.List;

public interface BookMapper {

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
}
