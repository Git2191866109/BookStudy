package com.litian.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: TestDBCP.java
 * @time: 2020/4/6 13:28
 * @desc: |使用DBCP数据库连接池
 */

public class TestDBCP {
    
    public static void main(String[] args) throws Exception {
        testDBCPwithDataSourceFactory();
    }

    /**
     * 利用DBCP工厂建立连接池
     */
    public static void testDBCPwithDataSourceFactory() throws Exception {
        Properties p = new Properties();
        InputStream is = TestDBCP.class.getClassLoader().getResourceAsStream("dbcp.properties");
        p.load(is);
        DataSource ds = BasicDataSourceFactory.createDataSource(p);
        System.out.println(ds.getConnection());

        BasicDataSource bs = (BasicDataSource) ds;
        System.out.println(bs.getMaxWaitMillis());
    }

    public static void testDBCP(){
        // 1. 创建DBCP数据源实例
        BasicDataSource ds = null;
        ds = new BasicDataSource();
        // 2. 为数据源实例指定必须的属性
        ds.setUsername("root");
        ds.setPassword("123456");
        ds.setUrl("jdbc:mysql://localhost:3306/testjdbc?serverTimezone=GMT%2B8");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // 3. 指定数据源的一些可选属性
        // 3.1 指定数据库连接池初始化连接数的个数
        ds.setInitialSize(10);
        // 3.2 指定最大的连接数，现在的代码中已经没有setMaxActive这个方法名了
        // 同一时刻可以同时向数据库申请的连接数
        ds.setMaxTotal(50);
        // 3.3 指定小连接数
        // 在数据库中连接池中最少有多少个空闲连接数
        ds.setMinIdle(5);
        // 3.4 等待连接池分配连接，最长的等待时间.单位为毫秒，超出该时间抛异常。
        ds.setMaxWaitMillis(1000 * 5);

        try {
            // 4. 从数据源中获取数据库连接
            Connection conn = ds.getConnection();
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
