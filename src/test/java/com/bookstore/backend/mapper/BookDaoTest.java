package com.bookstore.backend.mapper;

import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.entity.Comment;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bookstore.backend.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTest {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private PageHelper pageHelper;

    @Test
    public void findAllTest(){
        List<Book> bookList = bookDao.findBookByKey("人间失格");
        for (Book book:bookList) {
            System.out.println(book);
        }
    }

    @Test
    public void pageHelperTest(){
        PageHelper.startPage(71,10,"sale desc");
        List<Book> bookList = bookDao.findBookByCategory("文学");
        PageInfo<Book> page = new PageInfo<>(bookList);
        List<Book> books = page.getList();
        System.out.println("文学数目有：" + books.size());
        System.out.println("总的文学数目有：" + page.getTotal());
        for (Book book:books) {
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
        Comment comment = new Comment();
        comment.setBook_id("B00112Y93U");
        comment.setUser_id("U0001");
        comment.setDate(new Date());
        comment.setComment("好书");
        bookDao.insertBookComment(comment);
    }

    @Test
    public void updateBookSSTest(){
        bookDao.updateBookSS("B00112Y93U");
    }
}