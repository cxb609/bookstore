package com.bookstore.backend.dao;

import com.bookstore.backend.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShoppingCartDao {

    /**
     * 查找购物车
     * @param userId
     * @param bookId
     * @return
     */
    ShoppingCart getShoppingCart(@Param("userId") String userId, @Param("bookId") String bookId);

    /**
     * 根据用户ID查找其购物车
     * @param userId
     * @return
     */
    List<ShoppingCart> getShoppingCartsByUserId(String userId);

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    Integer addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 添加多个购物车
     * @param shoppingCarts
     * @return
     */
    Integer addShoppingCarts(@Param("shoppingCarts") List<ShoppingCart> shoppingCarts);

    /**
     * 删除购物车
     * @param userId
     * @param bookId
     * @return
     */
    Integer deleteShoppingCart(@Param("userId") String userId, @Param("bookId") String bookId);

    /**
     * 删除某个用户的购物车
     * @param userId
     * @return
     */
    Integer deleteShoppingCartsByUserId(String userId);

    /**
     * 修改购物车中某本书的数量
     * @param userId
     * @param bookId
     * @param newQuantity
     * @return
     */
    Integer modifyQuantity(@Param("userId") String userId, @Param("bookId") String bookId, @Param("newQuantity") int newQuantity);
}
