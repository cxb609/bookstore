package com.bookstore.backend.mapper;

import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.entity.Comment;
import com.bookstore.backend.entity.ShowBook;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bookstore.backend.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTest {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private PageHelper pageHelper;

    @Test
    public void findAllTest(){
        List<ShowBook> bookList = bookDao.findBookByKey("人间失格");
        for (ShowBook book:bookList) {
            System.out.println(book);
        }
    }

    @Test
    public void pageHelperTest(){
        PageHelper.startPage(71,10,"sale desc");
        List<ShowBook> bookList = bookDao.findBookByCategory("文学");
        PageInfo<ShowBook> page = new PageInfo<>(bookList);
        List<ShowBook> books = page.getList();
        System.out.println("文学数目有：" + books.size());
        System.out.println("总的文学数目有：" + page.getTotal());
        for (ShowBook book:books) {
            System.out.println(book);
        }
    }

    @Test
    public void getCommentsByIdTest(){
        List<Comment> commentList = bookDao.getBookCommentsById("B00112Y93U");
        System.out.println(commentList);
    }

    @Test
    public void insertBookCommentTest(){
        System.out.println(System.currentTimeMillis());
        Comment comment = new Comment();
        comment.setBook_id("B00112Y93U");
        comment.setUser_id("U0001");
        comment.setDate(new Timestamp(System.currentTimeMillis()));
        comment.setComment("好书");
        bookDao.insertBookComment(comment);
    }

    @Test
    public void updateBookSSTest(){
        bookDao.updateBookSS("B00112Y93U",5);
    }

    @Test
    public void getBookByIdTest(){
        Book book = bookDao.getBookById("B00112Y93U");
        System.out.println(book);
    }
}