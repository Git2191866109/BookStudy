package com.sxt.SORM.core;

import com.sxt.SORM.bean.Configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DBManager.java
 * @time: 2020/3/11 13:43
 * @desc: |根据配置信息，维持连接对象的管理（增加连接池功能）
 */

public class DBManager {
    private static Configuration conf;

    static {
        // 静态代码块
        Properties pros = new Properties();
        try {
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/sxt/SORM/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        conf = new Configuration();
        conf.setDriver(pros.getProperty("driver"));
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setURL(pros.getProperty("URL"));
        conf.setUser(pros.getProperty("user"));
        conf.setUsingDb(pros.getProperty("usingDB"));
    }

    public static Connection getConn() {
        /*获取数据库(mysql)驱动连接*/
        // 加载驱动类
        try {
            Class.forName(conf.getDriver());
            // 直接建立连接，后期增加连接池处理，提高效率！
            return DriverManager.getConnection(conf.getURL(), conf.getUser(), conf.getPwd());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn) {
        /*关闭接口方法*/
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement ps, Connection conn) {
        /*关闭接口方法，重载*/
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        /*关闭接口方法，重载*/
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
