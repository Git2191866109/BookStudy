package com.litian.mvc.dao.impl;

import com.litian.mvc.dao.CustomerDao;
import com.litian.mvc.dao.DAO;
import com.litian.mvc.domain.Customer;

import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: CustomerDAOJdbcImpl.java
 * @time: 2020/4/23 12:39
 * @desc: |
 */

public class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDao {
    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void save(Customer c) {

    }

    @Override
    public Customer get(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public long getCountWithNames(String name) {
        return 0;
    }
}
