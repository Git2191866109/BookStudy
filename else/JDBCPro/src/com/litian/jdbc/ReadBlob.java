package com.litian.jdbc;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: ReadBlob.java
 * @time: 2020/3/31 14:47
 * @desc: |读取Blob数据
 *      1. 使用getBlob方法读取到Blob对象
 *      2. 调用Blob的getBinaryStream()方法得到输入流。再使用IO操作即可。
 */

public class ReadBlob {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCTools.getConnection();
            String sql = "select id, username 姓名, pwd, pic from t_user where id=21028";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String pwd = rs.getString(3);
                System.out.println(id + "->" + name + "->" + pwd);
                Blob pic = rs.getBlob(4);
                InputStream in = pic.getBinaryStream();
                OutputStream out = new FileOutputStream("info.jpg");
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len=in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
    }
}
