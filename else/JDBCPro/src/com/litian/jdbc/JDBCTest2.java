package com.litian.jdbc;

import java.util.Scanner;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JDBCTest2.java
 * @time: 2020/3/22 14:47
 * @desc: |
 */

public class JDBCTest2 {

    public static void main(String[] args){
        new JDBCTest2().testAddNewUser();
    }

    public void testAddNewUser(){
        User user = getUserFromConsole();
        addNewUser(user);
    }

    /**
     * 从控制体输入学生的信息
     * @return
     */
    private User getUserFromConsole(){
        Scanner scanner = new Scanner(System.in);
        User u = new User();
        System.out.print("id：");
        u.setId(scanner.nextInt());
        System.out.print("username：");
        u.setUsername(scanner.next());
        System.out.print("pwd：");
        u.setPwd(scanner.next());
        return u;
    }

    public void addNewUser(User user) {
        // 1. 准备一条sql语句
        String sql = "insert into t_user values(" +
                user.getId() + ",'" +
                user.getUsername() + "','" +
                user.getPwd() + "'," +
                "now()" + "," +
                "now()" +
                ")";
        System.out.println(sql);
        // 2. 调用JDBCTools类的update(sql)方法执行插入操作
        new JDBCTest().update(sql);
    }
}
