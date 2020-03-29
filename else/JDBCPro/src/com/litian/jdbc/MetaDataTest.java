package com.litian.jdbc;

import java.sql.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: MetaDataTest.java
 * @time: 2020/3/29 15:12
 * @desc: |
 */

public class MetaDataTest {

    public static void main(String[] args){
        testDatabaseMetaData();
        testResultSetMetaData();
    }

    /**
     * ResultSetMetaData：描述结果集的元数据对象
     * 可以得到结果集中的基本信息：结果集中有哪些列，列名、列的别名等。
     * 结合反射可以写出通用的查询方法
     */
    public static void testResultSetMetaData(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCTools.getConnection();
            String sql = "select id, username 姓名, pwd from t_user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // 1. 得到ResultSetMetaData对象
            ResultSetMetaData rsmd = rs.getMetaData();

            // 2. 得到列的个数
            int columnCount = rsmd.getColumnCount();
            System.out.println(columnCount);

            for (int i = 0; i < columnCount; i++) {
                // 3. 得到列名
                String columnName = rsmd.getColumnName(i + 1);
                // 4. 得到列的别名
                String columnLabel = rsmd.getColumnLabel(i + 1);
                System.out.println(columnName + "-->" + columnLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
    }

    /**
     * DatabaseMetaData是描述数据库的元数据对象
     * 可以由Connection得到
     */
    public static void testDatabaseMetaData(){
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = JDBCTools.getConnection();
            DatabaseMetaData data = conn.getMetaData();

            // 可以得到数据库本身的一些基本信息
            // 1. 得到数据库的版本号
            int version = data.getDatabaseMajorVersion();
            System.out.println(version);

            // 2. 得到连接数据库的用户名
            String user = data.getUserName();
            System.out.println(user);

            // 3. 得到MySQL中有哪些数据库
            rs = data.getCatalogs();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, null, conn);
        }
    }
}
