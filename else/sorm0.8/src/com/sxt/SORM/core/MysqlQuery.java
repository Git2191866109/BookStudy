package com.sxt.SORM.core;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: MysqlQuery.java
 * @time: 2020/3/13 16:54
 * @desc: |负责针对mysql数据库的查询
 */

public class MysqlQuery extends Query {

    public static void main(String[] args) {
        Object obj = new MysqlQuery().queryValue("select count(*) from emp where salary>?", new Object[]{1000});
        System.out.println(obj);
    }

    @Override
    public Object queryPagenate(int pageNum, int size) {
        return null;
    }
}
