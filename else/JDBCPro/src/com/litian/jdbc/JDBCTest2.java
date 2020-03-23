package com.litian.jdbc;

import java.sql.*;
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
        new JDBCTest2().testAddNewUser();
        // new JDBCTest2().testPreparedStatement();
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
}
