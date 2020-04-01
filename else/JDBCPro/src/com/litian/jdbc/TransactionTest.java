package com.litian.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: TransactionTest.java
 * @time: 2020/4/1 14:06
 * @desc: |
 */

public class TransactionTest {
    public static void main(String[] args) {
        DAO dao = new DAO();

        Connection conn = null;

        try {
            conn = JDBCTools.getConnection();

            // 开始事务：取消默认提交
            conn.setAutoCommit(false);
            String sql = "update t_user2 set money = money - 500 where id = 21023";
            dao.update(conn, sql);

            // 插入错误
            int i = 10 / 0;
            System.out.println(i);

            sql = "update t_user2 set money = money + 500 where id = 21024";
            dao.update(conn, sql);

            // 提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }
}
