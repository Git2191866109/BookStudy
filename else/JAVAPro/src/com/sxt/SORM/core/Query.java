package com.sxt.SORM.core;

import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: Query.java
 * @time: 2020/3/10 17:31
 * @desc: |负责查询（对外提供服务的核心类）
 */

public interface Query {

    /**
     * 直接执行一个DML语句
     *
     * @param sql    sql语句
     * @param params 参数
     * @return 执行sql语句后影响记录的行数
     */
    public int executeDML(String sql, Object[] params);

    /**
     * 将一个对象存储到数据库中
     *
     * @param obj 要存储的对象
     */
    public void insert(Object obj);

    /**
     * 删除clazz表示类对应的表中的记录（指定主键id的记录）
     *
     * @param clazz 跟表对应的类的Class对象
     * @param id    主键的值
     */
    // delete from User where id = 2;
    public void delete(Class clazz, Object id);

    /**
     * 删除对象在数据库中对应的记录（对象所在类对应到表，对象的主键对应到的记录）
     *
     * @param obj
     */
    public void delete(Object obj);

    /**
     * 更新对象对应的记录，并且只更新指定的字段的值
     *
     * @param obj        索要更新的对象
     * @param fieldNames 更新的属性列表
     * @return 执行sql语句后影响记录的行数
     */
    // update user set uname=?, pwe=?
    public int update(Object obj, String[] fieldNames);

    /**
     * 查询返回多行记录，并将每行记录封装到clazz指定的类的对象中
     *
     * @param sql    查询语句
     * @param clazz  封装数据的javabean类的Class对象
     * @param params sql的参数
     * @return 返回查询到的结果
     */
    public List queryRows(String sql, Class clazz, Object[] params);

    /**
     * 查询返回一行记录，并将该记录封装到clazz指定的类的对象中
     *
     * @param sql    查询语句
     * @param clazz  封装数据的javabean类的Class对象
     * @param params sql的参数
     * @return 返回查询到的结果
     */
    public List queryUniqueRows(String sql, Class clazz, Object[] params);

    /**
     * 查询返回一个值（一行一列），并将该值返回
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 返回查询到的结果
     */
    public List queryValue(String sql, Object[] params);

    /**
     * 查询返回一个数字（一行一列），并将该值返回
     *
     * @param sql    查询语句
     * @param params sql的参数
     * @return 返回查询到的数字
     */
    public Number queryNumber(String sql, Object[] params);
}
