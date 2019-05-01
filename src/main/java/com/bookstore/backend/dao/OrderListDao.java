package com.bookstore.backend.dao;

import com.bookstore.backend.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface OrderListDao {

    /**
     * 根据用户ID查找其所有订单
     * @param userId
     * @return
     */
    List<OrderList> getOrdersByUserId(String userId);

    /**
     * 根据用户ID和日期范围查找所有订单
     * @param userId
     * @param start
     * @param end
     * @return
     */
    List<OrderList> getOrdersByUserIdAndDate(@Param("userId") String userId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 根据订单ID查找订单
     * @param orderId
     * @return
     */
    List<OrderList> getOrdersByOrderId(String orderId);

    /**
     * 添加订单
     * @param order
     */
    void addOrder(OrderList order);

    /**
     * 添加多个订单
     * @param orderList
     */
    void addOrders(@Param("orderList") List<OrderList> orderList);

    /**
     * 删除订单
     * @param orderId
     * @param bookId
     */
    void deleteOrder(@Param("orderId") String orderId, @Param("bookId") String bookId);

    /**
     * 根据订单ID删除所有订单
     * @param orderId
     */
    void deleteOrdersByOrderId(String orderId);

    /**
     * 修改订单的数量
     * @param orderId
     * @param bookId
     * @param newQuantity
     */
    void modifyQuantity(@Param("orderId") String orderId, @Param("bookId") String bookId, @Param("newQuantity") int newQuantity);
}
