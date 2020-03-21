package com.litian.jdbc;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JDBCTest.java
 * @time: 2019/12/15 18:56
 * @desc: JDBC试验，Driver是一个接口：数据库厂商必须提供实现的接口，能从其中获取数据库连接。
 */

public class JDBCTest {

    public static void main(String[] args) throws Exception {
        // new JDBCTest().testGetConnection();
        // new JDBCTest().testDriverManager();
        // Connection conn = new JDBCTest().getConnection2();
        // System.out.println(conn);
        new JDBCTest().testStatement();
        // new JDBCTest().testResultSet();
    }

    /**
     * ResultSet：结果集。封装了使用JDBC进行查询的结果。
     * 1. 调用Statement对象的executeQuery(sql)方法
     * 2. ResultSet返回的实际上就是一张数据表。有一个指针指向数据表的第一行的前面。
     * 可以调用next()方法检测下一行是否有效。若有效，该方法返回true，且指针下移。
     * 相当于Iterator对象的hasNext()和next()方法的结合体
     * 3. 当指针对应到一行时，可以通过嗲用getXXX(index)或getXXX(columnName)获取
     * 每一列的值。如：getInt(1)，getString("name")
     * 4. 关闭ResultSet
     */
    public void testResultSet(){
        // 获取各项记录，并打印
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            // 1. 获取Connection
            conn = JDBCTools.getConnection();
            // 2. 获取Statement
            statement = conn.createStatement();
            // 3. 准备SQL
            String sql = "select id, username, pwd, regTime, lastLoginTime from t_user";
            // 4. 执行查询，得到ResultSet
            rs = statement.executeQuery(sql);
            // 5. 处理ResultSet
            while(rs.next()){
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String pwd = rs.getString(3);
                Date regTime = rs.getDate(4);
                Timestamp lastLoginTime = rs.getTimestamp(5);
                System.out.println(id + "-->" + username + "-->" + pwd + "-->" + regTime + "-->" + lastLoginTime);
            }
            // 6. 关闭数据库资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(rs, statement, conn);
        }
    }

    /**
     * 通用的更新的方法：insert/update/delete
     * 版本1
     */
    public void update(String sql) {
        Connection conn = null;
        Statement statement = null;

        try {
            conn = getConnection2();
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, conn);
        }
    }


    /**
     * 通过JDBC向指定的数据表中插入一条记录
     * 1. Statement：用于执行sql语句的对象
     * 1.1 通过Connection的createStatement()方法来获取
     * 1.2 通过executeUpdate(sql)可以执行SQL语句
     * 1.3 传入的sql可以是insert, update或delete，但不能是select
     * 2. Connection、Statement都是应用程序和数据库服务器的连接资源。使用后一定要关闭。
     * 2.1 需要再finally中关闭
     * 3. 关闭顺序：先获取的后关，后获取的先关
     */
    public void testStatement() {
        Connection conn = null;
        Statement statement = null;
        try {
            // 1. 获取数据库连接
            conn = getConnection2();
            // 2. 准备插入的SQL语句
            String sql = "insert into t_user (username, pwd) values('测试', 3352);";
            String sql2 = "update t_user set username='傻瓜' where id = 20017";
            // 3. 执行插入
            // 3.1 获取操作sql语句的Statement对象
            statement = conn.createStatement();
            // 3.2 调用Statement对象的executeUpdate(sql)执行SQL语句
            statement.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    // 4. 关闭Statement对象
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null) {
                    // 5. 关闭Connection对象
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection2() throws Exception {
        // 1. 准备连接数据库的4个字符串。
        // 1.1 创建Properties对象
        Properties properties = new Properties();
        // 1.2 获取jdbc.properties对应的输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        // 1.3 加载1.2对应的输入流
        properties.load(in);
        // 1.4 具体决定user，password等4个字符串。
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String jdbcUrl = properties.getProperty("jdbcUrl");
        String driver = properties.getProperty("driver");
        // 2. 加载数据库驱动程序
        Class.forName(driver);
        // 3. 通过DriverManager的getConnection()方法获取数据库连接。
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    /**
     * DriverManager是驱动的管理类
     * 1. 可以通过重载的getConnection()方法获取数据库连接。较为方便
     * 2. 可以同时管理多个驱动程序：若注册了多个数据库连接，则调动getConnection()方法时
     * 传入的参数不同，则返回不同的数据库连接
     */
    public void testDriverManager() throws Exception {
        // 1. 准备连接数据库的4个字符串

        // 驱动的全类名
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;

        // 读取类路径下的jdbc.propertites 文件
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        // 2. 加载数据库驱动程序（对应的Driver实现类中有注册驱动的静态代码块程序）
        // 下面的注册程序已经写好了，不需要自己写
        // DriverManager.registerDriver((Driver) Class.forName(driverClass).newInstance());
        Class.forName(driverClass);

        // 3. 通过DriverManager的getConnection()方法获取数据库连接
        Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
        System.out.println(connection);

    }

    public void test1() throws SQLException {
        // 1. 创建一个Driver实现类的对象
        Driver driver = new com.mysql.jdbc.Driver();
        // 2. 准备连接数据库的基本信息：url，user，password
        String url = "jdbc:mysql://localhost:3306/girls";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "tian19951103");

        // 3. 调用Driver接口的connect(url, info)获取数据库连接
        Connection connection = driver.connect(url, info);
        System.out.println(connection);
    }

    // 编写一个通用的方法，在不修改源程序的情况下，可以获取任何数据库的连接
    public Connection getConnection() throws Exception {
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;

        // 读取类路径下的jdbc.propertites 文件
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        Driver driver = (Driver) Class.forName(driverClass).newInstance();

        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        Connection connection = driver.connect(jdbcUrl, info);

        return connection;
    }

    public void testGetConnection() throws Exception {
        System.out.println(getConnection());
    }

}
