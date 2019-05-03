package com.bookstore.backend.mapper;

import com.bookstore.backend.dao.AddressDao;
import com.bookstore.backend.dao.UserDao;
import com.bookstore.backend.entity.Address;
import com.bookstore.backend.entity.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Test
    public void getAddressesTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<Address> addresses1 = new ArrayList<>();
        Address address = new Address();
        address.setUser_id("U123456789");
        address.setAddress("广州");
        addresses1.add(address.clone());
        address.setAddress("揭阳");
        addresses1.add(address);
        addressDao.addAddresses(addresses1);
        List<Address> addresses2 = addressDao.getAddresses("U123456789");
        Assert.assertEquals(addresses1, addresses2);
        addressDao.deleteAddresses("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void addAddressTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<Address> addresses1 = new ArrayList<>();
        Address address = new Address();
        address.setUser_id("U123456789");
        address.setAddress("广州");
        addresses1.add(address.clone());
        address.setAddress("揭阳");
        addresses1.add(address);
        addressDao.addAddress(addresses1.get(0));
        addressDao.addAddress(addresses1.get(1));
        List<Address> addresses2 = addressDao.getAddresses("U123456789");
        Assert.assertEquals(addresses1, addresses2);
        addressDao.deleteAddresses("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void addAddressesTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<Address> addresses1 = new ArrayList<>();
        Address address = new Address();
        address.setUser_id("U123456789");
        address.setAddress("广州");
        addresses1.add(address.clone());
        address.setAddress("揭阳");
        addresses1.add(address);
        addressDao.addAddresses(addresses1);
        List<Address> addresses2 = addressDao.getAddresses("U123456789");
        Assert.assertEquals(addresses1, addresses2);
        addressDao.deleteAddresses("U123456789");
        userDao.deleteUser("U123456789");
    }

    @Test
    public void deleteAddressTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        Address address = new Address();
        address.setUser_id("U123456789");
        address.setAddress("广州");
        addressDao.addAddress(address);
        addressDao.deleteAddress("U123456789", "广州");
        List<Address> addresses1 = addressDao.getAddresses("U123456789");
        List<Address> addresses2 = new ArrayList<>();
        Assert.assertEquals(addresses2, addresses1);
        userDao.deleteUser("U123456789");
    }

    @Test
    public void deleteAddressesTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<Address> addresses1 = new ArrayList<>();
        Address address = new Address();
        address.setUser_id("U123456789");
        address.setAddress("广州");
        addresses1.add(address.clone());
        address.setAddress("揭阳");
        addresses1.add(address);
        addressDao.addAddresses(addresses1);
        addressDao.deleteAddresses("U123456789");
        List<Address> addresses2 = new ArrayList<>();
        Assert.assertEquals(addresses2, addressDao.getAddresses("U123456789"));
        userDao.deleteUser("U123456789");
    }

    @Test
    public void modifyAddressTest(){
        User user = new User();
        user.setUser_id("U123456789");
        user.setName("小兵");
        user.setPassword("123456");
        userDao.addUser(user);
        List<Address> addresses1 = new ArrayList<>();
        Address address = new Address();
        address.setUser_id("U123456789");
        address.setAddress("广州");
        addressDao.addAddress(address);
        address.setAddress("揭阳");
        addresses1.add(address);
        addressDao.modifyAddress("U123456789", "广州", "揭阳");
        List<Address> addresses2 = addressDao.getAddresses("U123456789");
        Assert.assertEquals(addresses1, addresses2);
        addressDao.deleteAddresses("U123456789");
        userDao.deleteUser("U123456789");
    }
}