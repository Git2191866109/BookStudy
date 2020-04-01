package com.litian.jdbc;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: DAO.java
 * @time: 2020/3/26 18:37
 * @desc: |
 */

public class DAO {
    // insert, update, delete 操作都可以包含在其中
    void update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, ps, conn);
        }
    }

    // 外部来处理Connection
    void update(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, ps, null);
        }
    }

    // 查询一条记录，返回对应的对象
    <T> T get(Class<T> clazz, String sql, Object... args) {
        List<T> result = getForList(clazz, sql, args);
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;

        /* 根据下面的方法进行了改写
        T entity = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1. 获取Connection
            conn = JDBCTools.getConnection();
            // 2. 获取PreparedStatement
            ps = conn.prepareStatement(sql);
            // 3. 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 4. 进行查询，得到ResultSet
            rs = ps.executeQuery();

            // 5. 若ResultSet中有记录，准备一个Map<String, Object>: 键：存放列的别名；值：存放列的值
            if (rs.next()) {
                Map<String, Object> values = new HashMap<>();
                // 6. 得到ResultSetMetaData对象
                ResultSetMetaData rsmd = rs.getMetaData();
                // 7. 处理ResultSet，把指针向下移动一个单位
                // 8. 由ResultSetMetaData对象得到结果集中有多少列
                int columnCount = rsmd.getColumnCount();
                // 9. 由ResultSetMetaData得到每一列的别名，由ResultSet得到具体每一列的值
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValue = rs.getObject(columnLabel);

                    // 10. 填充Map对象
                    values.put(columnLabel, columnValue);
                }
                // 11. 用反射创建Class对象的对象
                entity = clazz.newInstance();
                // 12. 遍历Map对象，用反射填充对象的属性值：属性名为Map中的Key，属性值为Map中的Value
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    String propertyName = entry.getKey();
                    Object value = entry.getValue();
                    BeanUtils.setProperty(entity, propertyName, value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
        return entity;

         */
    }

    // 查询多条记录，返回对应的对象的集合
    <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
        List<T> list = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. 得到结果集
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();

            // 2. 处理结果集，得到一个Map对应的List，其中一个Map对象就是一条记录，Map的Key为rs中列的别名，Value为列的值
            List<Map<String, Object>> values = handleResultSetToMapList(rs);
            // 3. 把Map的List转为clazz对应的List，其中Map的key即clazz对应的对象的propertyName，value即为clazz对应对象的propertyValue
            list = transferMapListToBeanList(clazz, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
        return list;
    }

    public <T> List<T> transferMapListToBeanList(Class<T> clazz, List<Map<String, Object>> values) throws Exception {
        // 12. 判断List是否为空集合，若不为空，则遍历List，得到一个个Map对象，再把一个Map对象转为一个Class参数对应的Object对象。
        List<T> result = new ArrayList<T>();
        T bean = null;
        if (values.size() > 0) {
            for (Map<String, Object> m : values) {
                bean = clazz.newInstance();
                for (Map.Entry<String, Object> entry : m.entrySet()) {
                    String propertyName = entry.getKey();
                    Object value = entry.getValue();

                    BeanUtils.setProperty(bean, propertyName, value);
                }
                result.add(bean);
            }
        }
        return result;
    }

    /**
     * 处理结果集，得到Map的一个List，其中一个Map对象对应一条记录
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<Map<String, Object>> handleResultSetToMapList(ResultSet rs) throws SQLException {
        // 5. 若ResultSet中有记录，准备一个List<Map<String, Object>>
        // 键：存放列的别名；值：存放列的值，其中一个Map对象对应着一条记录
        List<Map<String, Object>> values = new ArrayList<>();
        List<String> columnLabels = getColumnLabels(rs);
        // 7. 处理ResultSet，使用while循环
        Map<String, Object> maps = null;
        while (rs.next()) {
            maps = new HashMap<>();
            for (String columnLabel : columnLabels) {
                Object value = rs.getObject(columnLabel);

                maps.put(columnLabel, value);
            }
            // 11. 把一条记录的填充好的Map对象放入5准备的List中
            values.add(maps);
        }
        return values;
    }

    /**
     * 获取结果集ColumnLabel对应的结果集
     */
    private List<String> getColumnLabels(ResultSet rs) throws SQLException {
        List<String> labels = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            String columnLabel = rsmd.getColumnLabel(i + 1);
            labels.add(columnLabel);
        }
        return labels;
    }

    // 返回某条记录的某一个字段的值或一个统计的值（一共有多少记录等。）
    <E> E getForValue(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. 得到结果集
            conn = JDBCTools.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();

            // 2. 取得结果
            if (rs.next()){
                return (E) rs.getObject(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, ps, conn);
        }
        return null;
    }
}
