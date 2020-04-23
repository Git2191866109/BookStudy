package com.litian.mvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: StudentDao.java
 * @time: 2020/4/21 20:00
 * @desc: |
 */

public class StudentDao {

    public void deleteById(Integer id) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            String driverClass = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test_users?serverTimezone=GMT%2B8";
            String user = "root";
            String password = "123456";
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);

            String sql = "delete from users where id = ?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
    }

    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String driverClass = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/test_users?serverTimezone=GMT%2B8";
            String user = "root";
            String password = "123456";
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, user, password);

            String sql = "select id, user, password from users";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String psd = rs.getString(3);

                Student st = new Student(id, username, psd);
                students.add(st);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return students;
    }
}
