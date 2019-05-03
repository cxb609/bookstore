package com.bookstore.backend.dao;

import com.bookstore.backend.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

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
    List<OrderList> getOrdersByUserIdAndDate(@Param("userId") String userId, @Param("start") long start, @Param("end") long end);

    /**
     * 根据订单ID查找订单
     * @param orderId
     * @return
     */
    List<OrderList> getOrdersByOrderId(String orderId);

    /**
     * 添加订单
     * @param order
     * @return
     */
    Integer addOrder(OrderList order);

    /**
     * 添加多个订单
     * @param orderList
     * @return
     */
    Integer addOrders(@Param("orderList") List<OrderList> orderList);

    /**
     * 删除订单
     * @param orderId
     * @param bookId
     * @return
     */
    Integer deleteOrder(@Param("orderId") String orderId, @Param("bookId") String bookId);

    /**
     * 根据订单ID删除所有订单
     * @param orderId
     * @return
     */
    Integer deleteOrdersByOrderId(String orderId);

    /**
     * 修改订单的数量
     * @param orderId
     * @param bookId
     * @param newQuantity
     * @return
     */
    Integer modifyQuantity(@Param("orderId") String orderId, @Param("bookId") String bookId, @Param("newQuantity") int newQuantity);

    /**
     * 计算一次订单的总价格
     * @param orderId
     * @return
     */
    double getTotalPrice(@Param("orderId") String orderId);

    /**
     * 计算一次订单的总数量
     * @param orderId
     * @return
     */
    int getTotalQuantity(@Param("orderId") String orderId);

    /**
     * 获取该用户的所有订单号
     * @param userId
     * @return
     */
    List<String> getOrderId(@Param("userId") String userId);
}
