package com.litian.jdbc;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: BeanUtilsTest.java
 * @time: 2020/3/27 15:37
 * @desc: |测试工具包beanutils
 */

public class BeanUtilsTest {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 测试赋值操作
        Object obj = new User();
        System.out.println(obj);

        BeanUtils.setProperty(obj, "username", "二哈");
        System.out.println(obj);

        // 测试获取操作
        Object val = BeanUtils.getProperty(obj, "username");
        System.out.println(val);

    }
}
