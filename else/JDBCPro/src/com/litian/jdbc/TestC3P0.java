package com.litian.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: TestC3P0.java
 * @time: 2020/4/6 14:40
 * @desc: |使用C3P0数据库连接池
 */

public class TestC3P0 {

    public static void main(String[] args) throws Exception {
        // test2();
        test3();
    }

    public static void test3() throws Exception{
        Connection conn = JDBCTools.getDSConnection();
        System.out.println(conn);
    }

    public static void test2() throws Exception{
        DataSource ds = new ComboPooledDataSource("mysql-config");
        System.out.println(ds.getConnection());
        ComboPooledDataSource cpds = (ComboPooledDataSource) ds;
        System.out.println(((ComboPooledDataSource) ds).getMaxStatements());
    }

    public static void test1() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=GMT%2B8");
        cpds.setUser("root");
        cpds.setPassword("123456");
        System.out.println(cpds.getConnection());
    }
}
