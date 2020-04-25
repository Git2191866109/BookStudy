package com.litian.mvc.dao.impl;

import com.litian.mvc.dao.CriteriaCustomer;
import com.litian.mvc.dao.CustomerDao;
import com.litian.mvc.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: CustomerDAOJdbcImplTest.java
 * @time: 2020/4/24 11:07
 * @desc: |
 */

class CustomerDAOJdbcImplTest {

    private CustomerDao dao = new CustomerDAOJdbcImpl();

    @Test
    void getAll() {
        List<Customer> customers = dao.getAll();
        System.out.println(customers);
    }

    @Test
    void save() {
        Customer cc = new Customer();
        cc.setAddress("Shanghai");
        cc.setName("Jerry");
        cc.setPhone("13477778888");

        dao.save(cc);
    }

    @Test
    void get() {
        Customer c = dao.get(1);
        System.out.println(c);
    }

    @Test
    void delete() {
        dao.delete(1);
    }

    @Test
    void getCountWithNames() {
        long count = dao.getCountWithNames("ABC");
        System.out.println(count);
    }

    @Test
    void getForListWithCriteriaCustomer() {
        CriteriaCustomer cc = new CriteriaCustomer("k", null, null);
        List<Customer> customers = dao.getForListWithCriteriaCustomer(cc);
        System.out.println(customers);
    }
}