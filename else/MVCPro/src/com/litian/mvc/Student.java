package com.litian.mvc;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: Student.java
 * @time: 2020/4/21 19:57
 * @desc: |
 */

public class Student {
    private Integer id;
    private String user;
    private String password;

    public Student() {
    }

    public Student(Integer id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
