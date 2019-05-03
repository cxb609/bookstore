package com.bookstore.backend.service;
import com.bookstore.backend.dao.ShoppingCartDao;
import com.bookstore.backend.entity.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class ShoppingCartService {
    @Autowired
    ShoppingCartDao shoppingCartDao;

    /**
     * 添加商品到购物车
     * @param user_id
     * @param book_id
     * @param quantity
     * @return
     */
    public Result addBookToCart(String user_id,String book_id,int quantity){
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUser_id(user_id);
        shoppingCart.setQuantity(quantity);
        shoppingCart.setBook_id(book_id);
        Integer affectedRow = shoppingCartDao.addShoppingCart(shoppingCart);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"添加购物车失败");
        }
        return Result.OK("添加购物车成功").build();
    }

    /**
     * 删除购物车中的商品
     * @param user_id
     * @param book_id
     * @return
     */
    public Result deleteBookFromCart(String user_id,String book_id){
        Integer affectedRow = shoppingCartDao.deleteShoppingCart(user_id, book_id);
        if(affectedRow == 0){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"删除购物车失败");
        }
        return Result.OK("删除购物车成功").build();
    }

    /**
     * 修改购物车商品数量
     * @param user_id
     * @param book_id
     * @param quantity
     * @return
     */
    public Result updateBookQuantity(String user_id,String book_id,int quantity)
    {
        Integer affectedRow = shoppingCartDao.modifyQuantity(user_id,book_id,quantity);
        if(affectedRow==0)
        {
            Result result=addBookToCart(user_id,book_id,quantity);
            if(result.getStatusCode()!=200)
                throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"修改商品数量失败");
        }
        return Result.OK("修改商品数量成功").build();
    }

    /**
     * 获取用户购物车信息
     * @param user_id
     * @return
     */
    public  Result getUserCarts(String user_id)
    {
        List<ShoppingCart> cartList=shoppingCartDao.getShoppingCartsByUserId(user_id);
        if(cartList==null){
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION,"购物车信息获取失败");
        }
        Map<String, Object> resultData = new LinkedHashMap<>();
        PageInfo<ShoppingCart> page = new PageInfo<>(cartList);
        resultData.put("totalNum",page.getTotal());
        resultData.put("data",page.getList());
        return Result.OK(resultData).build();
    }

}
