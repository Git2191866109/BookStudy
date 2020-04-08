package com.litian.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DBUtilsDAO.java
 * @time: 2020/4/8 10:56
 * @desc: |访问数据的DAO接口
 * 里面定义好访问数据表的各种方法
 * T 是DAO处理的实体类的类型
 */

public interface DBUtilsDAO<T> {

    /**
     * 批量处理的方法
     *
     * @param conn
     * @param sql
     * @param args 填充占位符的Object[] 类型的可变参数
     */
    void batch(Connection conn, String sql, Object[]... args);

    /**
     * 返回具体的一个值，例如总人数，平均工资，某一个人的email等。
     *
     * @param conn
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    <E> E getForValue(Connection conn, String sql, Object... args);

    /**
     * 返回T的一个集合
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    List<T> getForList(Connection conn, String sql, Object... args);

    /**
     * 返回一个T的对象
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    T get(Connection conn, String sql, Object... args) throws SQLException;


    /**
     * insert update delete
     *
     * @param conn 数据库连接
     * @param sql  sql语句
     * @param args 填充占位符的可变参数
     */
    void update(Connection conn, String sql, Object... args);
}
