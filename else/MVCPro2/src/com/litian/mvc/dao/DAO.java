package com.litian.mvc.dao;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DAO.java
 * @time: 2020/4/23 12:22
 * @desc: |
 */

import java.util.List;

/**
 * 封装了基本的CRUD（增删改查）的方法，以供子类继承使用
 * 当前DAO直接在方法中获取数据库连接
 * 真个DAO采取DBUtils解决方案
 * @param <T> 当前DAO处理的实体类的类型是什么
 */
public class DAO<T> {

    private Class<T> clazz;

    /**
     * 返回某一个字段的值，例如返回某一条记录的id或name
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    public <E> E getForValue(String sql, Object... args){
        return null;
    }

    /**
     * 返回T所对应的List
     * @param sql
     * @param args
     * @return
     */
    public List<T> getForList(String sql, Object... args){
        return null;
    }

    /**
     * 返回对应的T的一个实体类的对象
     * @param sql
     * @param args
     * @return
     */
    public T get(String sql, Object... args){
        return null;
    }

    /**
     * 该方法封装了insert、delete和update操作
     * @param sql sql语句
     * @param args 填充sql语句的占位符
     */
    public void update(String sql, Object... args){}
}
