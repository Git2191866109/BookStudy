package com.litian.jdbc;

import java.sql.Connection;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: UserDaoTest.java
 * @time: 2020/4/8 11:10
 * @desc: |
 */

public class UserDaoTest {

    UserDao ud = new UserDao();

    public static void main(String[] args){
        new UserDaoTest().testGet();
    }

    public void testGet(){
        Connection conn = null;
        try {
            conn = JDBCTools.getDSConnection();
            String sql = "select id, username, pwd from t_user where id = ?";
            User u = (User) ud.get(conn, sql, 3);
            System.out.println(u);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, conn);
        }
    }
}
