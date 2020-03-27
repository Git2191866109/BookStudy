package com.litian.jdbc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DAOTest.java
 * @time: 2020/3/26 18:59
 * @desc: |
 */

public class DAOTest {
    public static void main(String[] args) {
        DAO dao = new DAO();
        // 测试update
        // String sql = "insert into t_user(id, username, pwd, regTime, lastLoginTime) values(?,?,?,?,?)";
        // dao.update(sql, 4, "李英俊", "123456", new Date(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        // 测试get
        // String sql = "select id, username, pwd, regTime, lastLoginTime from t_user where id=?";
        // User u = dao.get(User.class, sql, 4);
        // System.out.println(u);

        // 测试getForList
        // String sql2 = "select id, username, pwd, regTime, lastLoginTime from t_user where id<?";
        // List<User> us = dao.getForList(User.class, sql2, 10);
        // System.out.println(us);

        // 测试getForValue
        String sql3 = "select username from t_user where id = ?";
        String cc = dao.getForValue(sql3, 1);
        System.out.println(cc);
    }
}
