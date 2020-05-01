package com.litian.mvc.dao.factory;

import com.litian.mvc.dao.CustomerDao;
import com.litian.mvc.dao.impl.CustomerDAOJdbcImpl;
import com.litian.mvc.dao.impl.CustomerDAOXMLImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: CustomerDAOFactory.java
 * @time: 2020/5/1 18:17
 * @desc: |
 */

public class CustomerDAOFactory {

    private static CustomerDAOFactory instance = new CustomerDAOFactory();
    private String type = null;
    private Map<String, CustomerDao> daos = new HashMap<>();

    private CustomerDAOFactory() {
        daos.put("jdbc", new CustomerDAOJdbcImpl());
        daos.put("xml", new CustomerDAOXMLImpl());
    }

    public static CustomerDAOFactory getInstance() {
        return instance;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CustomerDao getCustomerDAO() {
        return daos.get(type);
    }
}
