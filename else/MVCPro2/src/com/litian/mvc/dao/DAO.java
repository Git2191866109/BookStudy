package com.litian.mvc.dao;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DAO.java
 * @time: 2020/4/23 12:22
 * @desc: |
 */

import com.litian.mvc.db.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 封装了基本的CRUD（增删改查）的方法，以供子类继承使用
 * 当前DAO直接在方法中获取数据库连接
 * 真个DAO采取DBUtils解决方案
 *
 * @param <T> 当前DAO处理的实体类的类型是什么
 */
public class DAO<T> {

    private QueryRunner queryRunner = new QueryRunner();

    private Class<T> clazz;

    public DAO() {
        // [参考连接](https://bbs.csdn.net/topics/360169835?depth_1-utm_source=distribute.pc_relevant.none-task-discussion_topic-BlogCommendFromBaidu-3&utm_source=distribute.pc_relevant.none-task-discussion_topic-BlogCommendFromBaidu-3)
        // [什么是泛型](https://segmentfault.com/a/1190000014120746)
        // getGenericSuperclass() 通过反射获取当前类表示的实体（类，接口，基本类型或void）
        // 的直接父类的Type，getActualTypeArguments()返回参数数组。

        // 得到这个类的泛型父类
        Type superClass = getClass().getGenericSuperclass();

        // 如果没有实现ParameterizedType接口,即不支持泛型
        if (superClass instanceof ParameterizedType) {

            // 如果支持泛型，返回表示此类型实际类型参数的Type对象的数组，
            // 数组里放的都是对应类型的Class，因为可能有多个，所以是数组。
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();

            if (typeArgs != null && typeArgs.length > 0) {
                if (typeArgs[0] instanceof Class) {
                    clazz = (Class<T>) typeArgs[0];
                }
            }
        }
    }

    /**
     * 返回某一个字段的值，例如返回某一条记录的id或name
     *
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public <E> E getForValue(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            return (E) queryRunner.query(conn, sql, new ScalarHandler<>(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConnection(conn);
        }

        return null;
    }

    /**
     * 返回T所对应的List
     *
     * @param sql
     * @param args
     * @return
     */
    public List<T> getForList(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConnection(conn);
        }

        return null;
    }

    /**
     * 返回对应的T的一个实体类的对象
     *
     * @param sql
     * @param args
     * @return
     */
    public T get(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConnection(conn);
        }

        return null;
    }

    /**
     * 该方法封装了insert、delete和update操作
     *
     * @param sql  sql语句
     * @param args 填充sql语句的占位符
     */
    public void update(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = JdbcUtils.getConnection();
            queryRunner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.releaseConnection(conn);
        }
    }
}
