package com.litian.jdbc;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JDBCTest2.java
 * @time: 2020/3/22 14:47
 * @desc: |
 */

public class JDBCTest2 {

    public static void main(String[] args) {
        // new JDBCTest2().testAddNewUser();
        // new JDBCTest2().testPreparedStatement();
        new JDBCTest2().testGet();
        // new JDBCTest2().testResultSetMetaData();
    }


    public void testAddNewUser() {
        User user = getUserFromConsole();
        addNewUser2(user);
    }

    /**
     * 从控制体输入学生的信息
     *
     * @return
     */
    private User getUserFromConsole() {
        Scanner scanner = new Scanner(System.in);
        User u = new User();
        System.out.print("id：");
        u.setId(scanner.nextInt());
        System.out.print("username：");
        u.setUsername(scanner.next());
        System.out.print("pwd：");
        u.setPwd(scanner.next());
        return u;
    }

    public void addNewUser2(User user) {
        String sql = "insert into t_user values(?,?,?,?,?)";
        JDBCTools.update(sql, user.getId(), user.getUsername(), user.getPwd(), new Date(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }

    public void addNewUser(User user) {
        // 1. 准备一条sql语句
        String sql = "insert into t_user values(" +
                user.getId() + ",'" +
                user.getUsername() + "','" +
                user.getPwd() + "'," +
                "now()" + "," +
                "now()" +
                ")";
        System.out.println(sql);
        // 2. 调用JDBCTools类的update(sql)方法执行插入操作
        JDBCTools.update(sql);
    }

    public void testPreparedStatement() {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JDBCTools.getConnection();
            String sql = "insert into t_user (id, username, pwd, regTime, lastLoginTime) values(?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, 2);
            ps.setString(2, "狗贼");
            ps.setString(3, "123456");
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(ps, connection);
        }
    }

    public User getUser(String sql, Object... args) {
        User u = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();

            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPwd(rs.getString(3));
                u.setRegTime(rs.getDate(4));
                u.setLastLoginTime(rs.getTimestamp(5));
            }
            // 6. 关闭数据库资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
        return u;
    }

    public void testGet() {
        String sql = "select id, username, pwd, regTime, lastLoginTime from t_user where id = ?";
        User user = get(User.class, sql, 1);
        System.out.println(user);

        String sql2 = "select id, username, pwd from t_user where id = ?";
        User user2 = get(User.class, sql2, 2);
        System.out.println(user2);
    }

    public void testResultSetMetaData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select id, username, pwd, regTime, lastLoginTime from t_user where id = ?";
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 1);
            rs = ps.executeQuery();
            Map<String, Object> values = new HashMap<>();

            // 1. 得到ResultSetMetaData对象
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                // 2. 打印每一列的列名
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValue = rs.getObject(columnLabel);
                    values.put(columnLabel, columnValue);
                }
            }
            System.out.println(values);
            Class clazz = User.class;
            User object = (User) clazz.newInstance();
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                System.out.println(fieldName + ": " + fieldValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
    }

    public <T> T get(Class<T> clazz, String sql, Object... args) {
        T entity = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            Map<String, Object> values = new HashMap<>();
            ResultSetMetaData rsmd = rs.getMetaData();

            if (rs.next()) {
                // 利用反射创建对象
                entity = clazz.newInstance();
                // 通过解析sql语句来判断到底选择了哪些列，以及需要为entity对象的哪些属性赋值
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValue = rs.getObject(columnLabel);
                    values.put(columnLabel, columnValue);
                }
            }
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();
                System.out.println(fieldName + ": " + fieldValue);
            }

            // 这里要加入ReflectionUtils方法，将map的内容写入entity中，并返回entity

            // 6. 关闭数据库资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
        return entity;
    }
}
