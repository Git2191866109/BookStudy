package com.litian.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: LoginServlet.java
 * @time: 2020/4/13 14:17
 * @desc: |
 */

public class LoginServlet4 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 设置中文乱码问题
        resp.setCharacterEncoding("utf-8");
        // 设置响应的内容类型
        resp.setContentType("text/html;charset=utf-8");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/test_users?serverTimezone=GMT%2B8";
            String ur = "root";
            String psd = "123456";
            conn = DriverManager.getConnection(url, ur, psd);
            String sql = "select count(id) from users where user=? and password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    out.println("登陆成功：" + username);
                } else {
                    out.println("登陆失败：" + username + "，自己赶紧滚！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
