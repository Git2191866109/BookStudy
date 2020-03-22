package com.litian.jdbc;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: User.java
 * @time: 2020/3/22 14:38
 * @desc: |
 */

public class User {
    private int id;
    private String username;
    private String pwd;
    private Date regTime;
    private Timestamp lastLoginTime;

    public User(int id, String username, String pwd, Date regTime, Timestamp lastLoginTime) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.regTime = regTime;
        this.lastLoginTime = lastLoginTime;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", regTime=" + regTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
