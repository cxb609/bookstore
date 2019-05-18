package com.bookstore.backend.controller;

import com.bookstore.backend.entity.ErrorCode;
import com.bookstore.backend.entity.Result;
import com.bookstore.backend.entity.ServiceException;
import com.bookstore.backend.entity.ShoppingCart;
import com.bookstore.backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 获取购物车信息
     * @param
     * @return
     */
    @RequestMapping(value={"/carts"}, method=GET, produces = "application/json;charset=UTF-8")
    public Result getCartInfo(){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
//        System.out.println(user_id);
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return shoppingCartService.getUserCarts(user_id);
    }

    /**
     * 增加商品到购物车
     * @param
     * @param cart
     * @return
     */
    @RequestMapping(value={"/carts"}, method=POST, produces = "application/json;charset=UTF-8")
    public Result addCart( @RequestParam("book_id") String book_id, @RequestParam("quantity") int quantity){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return shoppingCartService.addBookToCart(user_id, book_id, quantity);
    }

    /**
     * 删除购物车中的商品
     * @param cart
     * @return
     */
    @RequestMapping(value={"/carts"}, method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteCart(@RequestParam("book_id") String book_id){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return shoppingCartService.deleteBookFromCart(user_id, book_id);
    }

    /**
     * 修改购物车商品数量
     * @param
     * @param cart
     * @return
     */
    @RequestMapping(value={"/carts"}, method=PUT, produces = "application/json;charset=UTF-8")
    public Result modifyBookQuantity( @RequestParam("book_id") String book_id, @RequestParam("quantity") int quantity){
        //获取当前session，user_id存在即为登录状态
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user_id = (String) request.getSession().getAttribute("user_id");
        if (user_id == null)
            throw new ServiceException(ErrorCode.PARAM_ERR_COMMON, "未登录");
        return shoppingCartService.updateBookQuantity(user_id, book_id, quantity);
    }
}
