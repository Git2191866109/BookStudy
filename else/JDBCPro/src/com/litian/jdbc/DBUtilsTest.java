package com.litian.jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DBUtilsTest.java
 * @time: 2020/4/7 12:09
 * @desc: |测试DBUtils工具类
 */

public class DBUtilsTest {
    public static void main(String[] args) {
        // testUpdate();
        // testQuery();
        // testBeanHanlder();
        // testBeanListHanlder();
        // testMapHanlder();
        // testMapListHanlder();
        testScalarHanlder();
    }

    /**
     * ScalarHandler：把结果集转为一个数值（可以是任意基本数据类型和字符串）
     * 默认返回第1列第1行的值，所以多行多列也就返回第一个值
     */
    public static void testScalarHanlder() {
        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select username from t_user where id > ? && id < ?";
            Object result = qr.query(conn, sql, new ScalarHandler(), 2, 5);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }

    /**
     * MapListHandler：将结果集转为一个Map的list
     * Map对应查询的一条记录：键：sql查询的列名（不是列的别名），值：列的值。
     * 而MapListHandler：返回的是多条记录对应的Map的集合。
     */
    public static void testMapListHanlder() {
        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select id, username, pwd from t_user where id > ? && id < ?";
            List<Map<String, Object>> u = qr.query(conn, sql, new MapListHandler(), 2, 5);
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }

    /**
     * MapHandler：返回sql对应的第一条记录对应的Map对象
     * 键：sql查询的列名（不是列的别名），值：列的值。
     */
    public static void testMapHanlder() {
        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select id, username, pwd from t_user where id > ? && id < ?";
            Map<String, Object> u = qr.query(conn, sql, new MapHandler(), 2, 5);
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }

    /**
     * BeanListHandler：把结果集转为一个List，该List不为null，但可能为空集合(size()方法返回0)
     * 若sql语句的确能够查询到记录，List中存放创建BeanListHandler传入的Class对象对应的对象。
     */
    public static void testBeanListHanlder() {
        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select id, username, pwd from t_user where id > ? && id < ?";
            List<User> u = (List<User>) qr.query(conn, sql, new BeanListHandler(User.class), 2, 5);
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }

    /**
     * BeanHandler：把结果集的第一条记录转为创建BeanHandler对象时传入的Class参数对应的对象。
     */
    public static void testBeanHanlder() {
        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();
        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select id, username, pwd from t_user where id = ?";
            User u = (User) qr.query(conn, sql, new BeanHandler(User.class), 3);
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }

    /**
     * QueryRunner的query方法的返回值屈居于其ResultSetHandler参数的handle方法的返回值
     */
    public static void testQuery() {

        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();

        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select id, username, pwd from t_user";
            Object obj = qr.query(conn, sql, new MyResultSetHandler());
            System.out.println(obj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }

    }

    /**
     * 测试QueryRunner类的update方法
     * 该方法可用于insert、update和delete
     */
    public static void testUpdate() {
        // 1. 创建QueryRunner的实现类
        QueryRunner qr = new QueryRunner();
        // 2. 使用update方法
        String sql = "delete from t_user where id in (?, ?)";

        Connection conn = null;

        try {
            conn = JDBCTools.getDSConnection();
            qr.update(conn, sql, 1, 2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }

    // 静态内部类
    static class MyResultSetHandler implements ResultSetHandler {

        @Override
        public Object handle(ResultSet resultSet) throws SQLException {
            // System.out.println("handle。。。");
            // return "111";
            List<User> us = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String pwd = resultSet.getString(3);

                User u = new User(id, username, pwd, new Date(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
                us.add(u);
            }
            return us;
        }
    }
}
