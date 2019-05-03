package com.bookstore.backend.controller;

import com.bookstore.backend.entity.Result;
import com.bookstore.backend.entity.ShoppingCart;
import com.bookstore.backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 获取购物车信息
     * @param user_id
     * @return
     */
    @RequestMapping(value={"/carts/{user_id}"}, method=GET, produces = "application/json;charset=UTF-8")
    public Result getCartInfo(@PathVariable("user_id") String user_id){
        return shoppingCartService.getUserCarts(user_id);
    }

    /**
     * 增加商品到购物车
     * @param user_id
     * @param cart
     * @return
     */
    @RequestMapping(value={"/carts/{user_id}"}, method=POST, produces = "application/json;charset=UTF-8")
    public Result addCart(@PathVariable("user_id") String user_id, @RequestBody ShoppingCart cart){
        return shoppingCartService.addBookToCart(user_id,cart.getBook_id(),cart.getQuantity());
    }

    /**
     * 删除购物车中的商品
     * @param user_id
     * @param cart
     * @return
     */
    @RequestMapping(value={"/carts/{user_id}"}, method=DELETE, produces = "application/json;charset=UTF-8")
    public Result deleteCart(@PathVariable("user_id") String user_id, @RequestBody ShoppingCart cart){
        return shoppingCartService.deleteBookFromCart(user_id,cart.getBook_id());
    }

    /**
     * 修改购物车商品数量
     * @param user_id
     * @param cart
     * @return
     */
    @RequestMapping(value={"/carts/{user_id}"}, method=PUT, produces = "application/json;charset=UTF-8")
    public Result modifyBookQuantity(@PathVariable("user_id") String user_id, @RequestBody ShoppingCart cart){
        return shoppingCartService.updateBookQuantity(user_id,cart.getBook_id(), cart.getQuantity());
    }
}
