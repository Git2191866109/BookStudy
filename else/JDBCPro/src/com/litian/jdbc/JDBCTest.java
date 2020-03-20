package com.litian.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
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

    public Connection getConnection2() throws Exception {
        // 1. 准备连接数据库的4个字符串。
        // 1.1 创建Properties对象
        Properties properties = new Properties();
        // 1.2 获取jdbc.properties对应的输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        // 1.3 加载1.2对应的输入流
        properties.load(in);
        // 1.4 具体决定user，password等4个字符串。
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String jdbcUrl = properties.getProperty("jdbcUrl");
        String driver = properties.getProperty("driver");
        // 2. 加载数据库驱动程序
        Class.forName(driver);
        // 3. 通过DriverManager的getConnection()方法获取数据库连接。
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    /**
     * DriverManager是驱动的管理类
     * 1. 可以通过重载的getConnection()方法获取数据库连接。较为方便
     * 2. 可以同时管理多个驱动程序：若注册了多个数据库连接，则调动getConnection()方法时
     *    传入的参数不同，则返回不同的数据库连接
     */
    public void testDriverManager() throws Exception {
        // 1. 准备连接数据库的4个字符串

        // 驱动的全类名
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

        // 2. 加载数据库驱动程序（对应的Driver实现类中有注册驱动的静态代码块程序）
        // 下面的注册程序已经写好了，不需要自己写
        // DriverManager.registerDriver((Driver) Class.forName(driverClass).newInstance());
        Class.forName(driverClass);

        // 3. 通过DriverManager的getConnection()方法获取数据库连接
        Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
        System.out.println(connection);

    }

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
        // new JDBCTest().testGetConnection();
        // new JDBCTest().testDriverManager();
        Connection conn = new JDBCTest().getConnection2();
        System.out.println(conn);
    }

}
