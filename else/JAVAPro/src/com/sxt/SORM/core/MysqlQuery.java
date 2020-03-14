package com.sxt.SORM.core;

import com.sxt.SORM.bean.ColumnInfo;
import com.sxt.SORM.bean.TableInfo;
import com.sxt.SORM.po.Emp;
import com.sxt.SORM.utils.JDBCUtils;
import com.sxt.SORM.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public static void main(String[] args) {
        Emp e = new Emp();
        e.setEmpname("Tom");
        e.setBirthday(new java.sql.Date(System.currentTimeMillis()));
        e.setAge(30);
        e.setSalary(8888.0);
        e.setId(1);

        // new MysqlQuery().delete(e);
        // new MysqlQuery().insert(e);
        new MysqlQuery().update(e, new String[]{"empname", "age", "salary"});
    }

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
        // obj --> 表中。 insert into 表名（id, name, pwd） values (?, ?, ?)
        Class c = obj.getClass();
        // 存储sql的参数对象
        List<Object> params = new ArrayList<>();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        StringBuilder sql = new StringBuilder("insert into " + tableInfo.getTname() + " (");

        // 计算不为空的属性值
        int countNotNullField = 0;

        // 目前只能处理数据库来维护自增的方式
        Field[] fs = c.getDeclaredFields();
        for (Field f : fs) {
            String fieldName = f.getName();
            Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
            if (fieldValue != null) {
                // 如果该属性值不为空
                countNotNullField++;
                sql.append(fieldName + ",");
                params.add(fieldValue);
            }
        }

        // 把最后一个属性后面的,换成)
        sql.setCharAt(sql.length() - 1, ')');
        sql.append(" values (");

        for (int i = 0; i < countNotNullField; i++) {
            sql.append("?,");
        }
        sql.setCharAt(sql.length() - 1, ')');

        executeDML(sql.toString(), params.toArray());
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
        // obj{"uname", "pwd} --> update 表名 set uname=?, pwd=? where id=?
        Class c = obj.getClass();
        List<Object> params = new ArrayList<>();
        TableInfo tableInfo = TableContext.poClassTableMap.get(c);
        ColumnInfo priKey = tableInfo.getOnlyPriKey();
        StringBuilder sql = new StringBuilder("update " + tableInfo.getTname() + " set ");

        for (String fname : fieldNames) {
            Object fvalue = ReflectUtils.invokeGet(fname, obj);
            params.add(fvalue);
            sql.append(fname + "=?,");
        }
        sql.setCharAt(sql.length() - 1, ' ');
        sql.append(" where ");
        sql.append(priKey.getName() + "=?");
        params.add(ReflectUtils.invokeGet(priKey.getName(), obj));

        return executeDML(sql.toString(), params.toArray());
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
}
