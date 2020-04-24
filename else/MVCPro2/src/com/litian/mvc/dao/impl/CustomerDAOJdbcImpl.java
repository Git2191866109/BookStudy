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
        String sql = "select id, name, address, phone from customers";
        return getForList(sql);
    }

    @Override
    public void save(Customer c) {
        String sql = "insert into customers(name, address, phone) values (?, ?, ?)";
        update(sql, c.getName(), c.getAddress(), c.getPhone());
    }

    @Override
    public Customer get(Integer id) {
        String sql = "select id, name, address, phone from customers where id = ?";
        return get(sql, id);
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from customers where id = ?";
        update(sql, id);
    }

    @Override
    public long getCountWithNames(String name) {
        String sql = "select count(id) from customers where name = ?";
        return getForValue(sql, name);
    }
}
