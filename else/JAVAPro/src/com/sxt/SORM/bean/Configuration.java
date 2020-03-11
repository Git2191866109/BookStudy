package com.sxt.SORM.bean;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: Configuration.java
 * @time: 2020/3/11 13:55
 * @desc: |管理配置信息
 */

public class Configuration {
    // 正在使用哪个数据库
    private String usingDb;
    // jdbc的url
    private String URL;
    // 驱动类
    private String driver;
    // 数据库的用户名
    private String user;
    // 数据库的密码
    private String pwd;
    // 项目的源码路径
    private String srcPath;
    // 扫描生成java类的包（po的意思是Persistence Object持久化对象）
    private String poPackage;

    public Configuration() {
    }

    public Configuration(String usingDb, String URL, String driver, String user, String pwd, String srcPath, String poPackage) {
        this.usingDb = usingDb;
        this.URL = URL;
        this.driver = driver;
        this.user = user;
        this.pwd = pwd;
        this.srcPath = srcPath;
        this.poPackage = poPackage;
    }

    public String getUsingDb() {
        return usingDb;
    }

    public void setUsingDb(String usingDb) {
        this.usingDb = usingDb;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getPoPackage() {
        return poPackage;
    }

    public void setPoPackage(String poPackage) {
        this.poPackage = poPackage;
    }
}
