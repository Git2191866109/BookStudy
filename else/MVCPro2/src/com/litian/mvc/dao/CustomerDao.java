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
    /**
     * 返回满足查询条件的list
     * @param cc 封装了查询条件
     * @return
     */
    public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc);

    public List<Customer> getAll();

    public void save(Customer c);

    public Customer get(Integer id);

    public void delete(Integer id);

    public void update(Customer customer);

    /**
     * 返回和name相等的记录数
     * @param name
     * @return
     */
    public long getCountWithNames(String name);
}
