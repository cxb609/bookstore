package com.bookstore.backend.dao;

import com.bookstore.backend.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AddressDao {

    /**
     * 根据用户ID查找其所有地址
     * @param userId
     * @return
     */
    List<Address> getAddresses(@Param("userId") String userId);

    /**
     * 添加地址
     * @param address
     * @return
     */
    Integer addAddress(@Param("address") Address address);

    /**
     * 添加多个地址
     * @param addresses
     * @return
     */
    Integer addAddresses(@Param("addresses") List<Address> addresses);

    /**
     * 删除地址
     * @param userId
     * @param address
     * @return
     */
    Integer deleteAddress(@Param("userId") String userId, @Param("address") String address);

    /**
     * 删除某个用户的所有地址
     * @param userId
     * @return
     */
    Integer deleteAddresses(@Param("userId") String userId);

    /**
     * 修改地址
     * @param userId
     * @param address
     * @param newAddress
     * @return
     */
    Integer modifyAddress(@Param("userId") String userId, @Param("address") String address, @Param("newAddress") String newAddress);
}
