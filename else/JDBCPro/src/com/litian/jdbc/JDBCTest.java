package com.litian.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JDBCTest.java
 * @time: 2019/12/15 18:56
 * @desc: JDBC试验，Driver是一个接口：数据库厂商必须提供实现的接口，能从其中获取数据库连接。
 */

public class JDBCTest {

    public void test1() throws SQLException {
        // 1. 创建一个Driver实现类的对象
        Driver driver = new com.mysql.jdbc.Driver();
        // 2. 准备连接数据库的基本信息：url，user，password
        String url = "jdbc:mysql://localhost:3306/girls";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "tian19951103");

        // 3. 调用Driver接口的connect(url, info)获取数据库连接
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    // 编写一个通用的方法，在不修改源程序的情况下，可以获取任何数据库的连接
    public Connection getConnection() throws Exception {
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;

        // 读取类路径下的jdbc.propertites 文件
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        Driver driver = (Driver) Class.forName(driverClass).newInstance();

        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        Connection connection = driver.connect(jdbcUrl, info);

        return connection;
    }

    public void testGetConnection() throws Exception {
        System.out.println(getConnection());
    }

    public static void main(String[] args) throws Exception {
        new JDBCTest().testGetConnection();
    }

}
