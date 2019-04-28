package com.bookstore.backend.mapper;

import com.bookstore.backend.entity.Comment;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bookstore.backend.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookMapperTest {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private PageHelper pageHelper;

    @Test
    public void findAllTest(){
        List<Book> bookList = bookMapper.findBookByKey("人间失格");
        for (Book book:bookList) {
            System.out.println(book);
        }
    }

    @Test
    public void pageHelperTest(){
        PageHelper.startPage(71,10,"sale desc");
        List<Book> bookList = bookMapper.findBookByCategory("文学");
        PageInfo<Book> page = new PageInfo<>(bookList);
        List<Book> books = page.getList();
        System.out.println("文学数目有：" + bookList.size());
        System.out.println("总的文学数目有：" + page.getTotal());
        for (Book book:bookList) {
            System.out.println(book);
        }
    }

    @Test
    public void getCommentsByIdTest(){
        List<Comment> commentList = bookMapper.getBookCommentsById("B00112Y93U");
        System.out.println(commentList);
    }
}