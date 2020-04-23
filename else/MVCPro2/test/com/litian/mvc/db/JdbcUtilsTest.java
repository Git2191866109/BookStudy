package com.litian.mvc.db;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JdbcUtilsTest.java
 * @time: 2020/4/23 16:31
 * @desc: |
 */

class JdbcUtilsTest {

    @org.junit.jupiter.api.Test
    void getConnection() throws SQLException {
        Connection conn = JdbcUtils.getConnection();
        System.out.println(conn);
    }
}