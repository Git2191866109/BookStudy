package com.litian.jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JdbcDaoImpl.java
 * @time: 2020/4/8 11:07
 * @desc: |使用QueryRunner提供其具体的实现
 * <T>为子类需传入的泛型类型
 */

public class JdbcDaoImpl<T> implements DBUtilsDAO {

    private QueryRunner qr = null;
    private Class<T> type;

    public JdbcDaoImpl(){
        qr = new QueryRunner();
        type = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    @Override
    public void batch(Connection conn, String sql, Object[]... args) {

    }

    @Override
    public List getForList(Connection conn, String sql, Object... args) {
        return null;
    }

    @Override
    public Object get(Connection conn, String sql, Object... args) throws SQLException {
        return qr.query(conn, sql, new BeanHandler<>(type), args);
    }

    @Override
    public void update(Connection conn, String sql, Object... args) {

    }

    @Override
    public Object getForValue(Connection conn, String sql, Object... args) {
        return null;
    }
}
