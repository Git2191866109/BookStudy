package com.litian.mvc.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JdbcUtils.java
 * @time: 2020/4/23 12:30
 * @desc: |JDBC操作的工具类
 */

public class JdbcUtils {

    private static DataSource dataSource = null;

    static {
        // 数据源只能被创建一次
        dataSource = new ComboPooledDataSource("mvcapp");
    }


    /**
     * 释放Connection连接
     * @param conn
     */
    public static void releaseConnection(Connection conn) {

    }


    /**
     * 返回数据源的一个Connection对象
     * @return
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
