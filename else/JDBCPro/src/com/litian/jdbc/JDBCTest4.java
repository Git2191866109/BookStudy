package com.litian.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JDBCTest4.java
 * @time: 2020/4/4 14:18
 * @desc: |批量处理事务
 */

public class JDBCTest4 {

    public static void main(String[] args){
        // testStatement();
        testPrepareStatement();
    }

    /**
     * 向数据库的数据表中插入10w条记录
     * 测试如何插入，用时最短
     * 1. 使用Statement：15038
     */
    public static void testStatement() {
        Connection conn = null;
        Statement st = null;
        String sql = null;
        try {
            conn = JDBCTools.getConnection();
            JDBCTools.beginTx(conn);
            st = conn.createStatement();

            long begin = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                sql = String.format("insert into t_user2(username, pwd) values(name_%d, 6666)", i);
                st.executeUpdate(sql);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - begin);

            JDBCTools.commit(conn);
        } catch (Exception e) {
            e.printStackTrace();
            JDBCTools.rollback(conn);
        } finally {
            JDBCTools.release(null, st, conn);
        }
    }

    /**
     * 向数据库的数据表中插入10w条记录
     * 测试如何插入，用时最短
     * 2. 使用PreparedStatement：13131
     * 3. 在2的基础上使用批量处理：24596?这就很尴尬了
     */
    public static void testPrepareStatement() {
        Connection conn = null;
        PreparedStatement st = null;
        String sql = null;
        try {
            conn = JDBCTools.getConnection();
            JDBCTools.beginTx(conn);
            sql = "insert into t_user2(username, pwd) values(?,?)";
            st = conn.prepareStatement(sql);

            long begin = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                st.setString(1, "name_" + i);
                st.setString(2, "6666");
                st.executeUpdate();

                // “积攒”sql语句
                st.addBatch();;
                // 当积攒到一定程度，就统一地执行一次，并且清空先前积攒的sql
                if((i + 1) % 300 == 0){
                    st.executeBatch();
                    st.clearBatch();
                }
            }

            // 若总条数不是批量数值的整数倍，则还需要额外再执行一次
            if(100000 % 300 != 0){
                st.executeBatch();
                st.clearBatch();
            }

            long end = System.currentTimeMillis();
            System.out.println(end - begin);

            JDBCTools.commit(conn);
        } catch (Exception e) {
            e.printStackTrace();
            JDBCTools.rollback(conn);
        } finally {
            JDBCTools.release(null, st, conn);
        }
    }
}
