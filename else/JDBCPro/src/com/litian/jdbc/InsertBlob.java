package com.litian.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: InsertBlob.java
 * @time: 2020/3/31 14:40
 * @desc: |插入Blob类型的数据必须使用PreparedStatement，因为BLOB类型的数据是无法使用字符串拼写的
 *          调用setBlob方法插入BLOB
 */

public class InsertBlob {
    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCTools.getConnection();
            String sql = "insert into t_user(username, pwd, pic) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "picture");
            ps.setString(2, "123456");
            InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\参考投稿进程.jpg");
            ps.setBlob(3, is);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, ps, conn);
        }
    }
}
