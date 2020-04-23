package com.litian.mvc.dao;

import com.litian.mvc.domain.Customer;

import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: CustomerDao.java
 * @time: 2020/4/23 12:31
 * @desc: |
 */

public interface CustomerDao {
    public List<Customer> getAll();

    public void save(Customer c);

    public Customer get(Integer id);

    public void delete(Integer id);

    /**
     * 返回和name相等的记录数
     * @param name
     * @return
     */
    public long getCountWithNames(String name);
}
