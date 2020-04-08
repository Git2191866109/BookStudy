package com.litian.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: TestCalllableStatement.java
 * @time: 2020/4/8 12:14
 * @desc: |如何使用JDBC调用存储在数据库中的函数或存储过程
 */

public class TestCalllableStatement {
    public static void main(String[] args){
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = JDBCTools.getDSConnection();

            // 1. 通过Connection对象的prepareCall()方法创建一个CallableStatement对象的实例。
            String sql = "{?= call sum_salary (?, ?)}";
            cs = conn.prepareCall(sql);

            // 2. 通过CallableStatement对象的registerOutParameter()方法注册OUT参数
            cs.registerOutParameter(1, Types.VARCHAR);
            // 3. 通过CallableStatement对象的setXxx()方法设定IN或IN OUT参数
            cs.setString(2, "你大爷");
            cs.setInt(3, 3);
            // 4. 执行存储过程
            cs.execute();
            // 5. 获取返回值
            String result = cs.getString(1);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, cs, conn);
        }
    }
}
