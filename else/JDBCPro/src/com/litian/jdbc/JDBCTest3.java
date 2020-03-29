package com.litian.jdbc;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JDBCTest3.java
 * @time: 2020/3/29 15:27
 * @desc: |取得数据库自动生成的主键值
 */

public class JDBCTest3 {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCTools.getConnection();
            String sql = "insert into t_user(username, pwd) values(?,?)";
            // ps = conn.prepareStatement(sql);

            // 使用重载的ps方法来生成ps对象
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "pika");
            ps.setString(2, "123456");
            ps.executeUpdate();

            // 通过getGeneratedKeys方法获取包含了新生成的主键的ResultSet对象
            // 在ResultSet中只有一列GENERATED_KEYS，用于存放新生成的主键值
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                System.out.println(rs.getInt(1));
            }

            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i + 1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, ps, conn);
        }
    }
}
