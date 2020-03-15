package com.sxt.SORM.core;

import com.sxt.SORM.bean.ColumnInfo;
import com.sxt.SORM.bean.TableInfo;
import com.sxt.SORM.po.Emp;
import com.sxt.SORM.utils.JDBCUtils;
import com.sxt.SORM.utils.ReflectUtils;
import com.sxt.SORM.vo.EmpVO;

import java.lang.reflect.Field;
import java.sql.*;
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
        Object obj = new MysqlQuery().queryValue("select count(*) from emp where salary>?", new Object[]{1000});
        System.out.println(obj);
    }

    /**
     * 复杂多行查询测试
     */
    public static void testQueryRows() {
        List<Emp> list = new MysqlQuery().queryRows("select id,empname,age from emp where age>? and salary<?", Emp.class,
                new Object[]{1, 9000});
        for (Emp e : list) {
            System.out.println(e.getEmpname());
        }

        String sql2 = "select e.id,e.empname,salary+bonus 'xinshui',age,d.dname 'deptName',d.address 'deptAddr' from emp e"
                + " " + "join dept d on e.deptId=d.id;";
        List<EmpVO> list2 = new MysqlQuery().queryRows(sql2, EmpVO.class, null);
        for (EmpVO e : list2) {
            System.out.println(e.getEmpname() + "-" + e.getDeptAddr() + "-" + e.getXinshui());
        }
    }

    /**
     * 增删改操作测试
     */
    public static void testDML() {
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
        Connection conn = DBManager.getConn();
        // 存放查询结果的容器
        List list = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            // 给sql设置参数，就是？位置的参数
            JDBCUtils.handleParams(ps, params);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            // 多行
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList();
                }
                // 调用javabean的无参构造器
                Object rowObj = clazz.newInstance();
                // 多列 select username, pwd, age from user where id>? and age>?
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    // username
                    String columnName = metaData.getColumnLabel(i + 1);
                    Object columnValue = rs.getObject(i + 1);

                    // 调用rowObj对象的setUsername(String uname)方法，将columnValue的值设置进去
                    ReflectUtils.invokeSet(rowObj, columnName, columnValue);
                }
                list.add(rowObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(ps, conn);
        }
        return list;
    }

    @Override
    public Object queryUniqueRows(String sql, Class clazz, Object[] params) {
        List list = queryRows(sql, clazz, params);
        return (list == null && list.size() > 0) ? null : list.get(0);
    }

    @Override
    public Object queryValue(String sql, Object[] params) {
        Connection conn = DBManager.getConn();
        // 存储查询结果的对象
        Object value = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            // 给sql设置参数，就是？位置的参数
            JDBCUtils.handleParams(ps, params);
            rs = ps.executeQuery();
            // 多行
            while (rs.next()) {
                // select count(*) from user
                value = rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBManager.close(ps, conn);
        }
        return value;
    }

    @Override
    public Number queryNumber(String sql, Object[] params) {
        return (Number) queryValue(sql, params);
    }
}
