package com.sxt.SORM.core;

import com.sxt.SORM.bean.ColumnInfo;
import com.sxt.SORM.bean.TableInfo;
import com.sxt.SORM.po.Emp;
import com.sxt.SORM.utils.JDBCUtils;
import com.sxt.SORM.utils.ReflectUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: MysqlQuery.java
 * @time: 2020/3/13 16:54
 * @desc: |负责针对mysql数据库的查询
 */

public class MysqlQuery implements Query {
    @Override
    public int executeDML(String sql, Object[] params) {
        Connection conn = DBManager.getConn();
        int count = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            // 给sql设置参数，就是？位置的参数
            JDBCUtils.handleParams(ps, params);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(ps, conn);
        }
        return count;
    }

    @Override
    public void insert(Object obj) {
    }

    @Override
    public void delete(Class clazz, Object id) {
        // Emp.class, 2 --> delete from emp where id=2
        // 通过Class对象找TableInfo
        TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
        // 获得主键
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();

        String sql = "delete from " + tableInfo.getTname() + " where " + onlyPriKey.getName() + "=?;";
        executeDML(sql, new Object[]{id});
    }

    @Override
    public void delete(Object obj) {
        Class c = obj.getClass();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        // 获得主键
        ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
        // 通过反射机制，调用属性对应的get方法或set方法
        Object priKeyValue = ReflectUtils.invokeGet(onlyPriKey.getName(), obj);
        delete(obj.getClass(), priKeyValue);
    }

    @Override
    public int update(Object obj, String[] fieldNames) {
        return 0;
    }

    @Override
    public List queryRows(String sql, Class clazz, Object[] params) {
        return null;
    }

    @Override
    public List queryUniqueRows(String sql, Class clazz, Object[] params) {
        return null;
    }

    @Override
    public List queryValue(String sql, Object[] params) {
        return null;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        return null;
    }

    public static void main(String[] args){
        Emp e = new Emp();
        e.setId(4);

        new MysqlQuery().delete(e);
    }
}
