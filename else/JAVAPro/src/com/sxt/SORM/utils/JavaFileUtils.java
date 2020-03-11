package com.sxt.SORM.utils;

import com.sxt.SORM.bean.ColumnInfo;
import com.sxt.SORM.bean.JavaFieldGetSet;
import com.sxt.SORM.core.MySqlTypeConvertor;
import com.sxt.SORM.core.TypeConvertor;

/**
 * @author: Li Tian
 * @contact: litian_cup@163.com
 * @software: IntelliJ IDEA
 * @file: JavaFileUtils.java
 * @time: 2020/3/11 13:44
 * @desc: | 封装了生成java文件（源代码）常用操作
 */

public class JavaFileUtils {
    /**
     * 根据字段信息生成java属性信息。如varchar username --> private String username;以及相应的set和get方法源码
     * @param column 字段信息
     * @param convertor 类型转化器
     * @return java属性和set/get方法源码
     */
    public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column, TypeConvertor convertor){
        JavaFieldGetSet jfgs = new JavaFieldGetSet();
        String javaFieldType = convertor.databaseType2JavaType(column.getDataType());
        jfgs.setFieldInfo("\tprivate " + javaFieldType + " " + column.getName() + ";\n");

        // public String getUsername(){return username;}
        StringBuilder getSrc = new StringBuilder();
        getSrc.append("\tpublic " + javaFieldType + " get" + StringUtils.firstChar2UpperCase(column.getName()) + "(){\n");
        getSrc.append("\t\treturn " + column.getName() + ";\n");
        getSrc.append("\t}\n");
        jfgs.setGetInfo(getSrc.toString());

        // public void setUsername(String username){this.username = username;}
        StringBuilder setSrc = new StringBuilder();
        setSrc.append("\tpublic void set" + StringUtils.firstChar2UpperCase(column.getName()) + "(");
        setSrc.append(javaFieldType + " " + column.getName() + "){\n");
        setSrc.append("\t\tthis." + column.getName() + " = " + column.getName() + ";\n");
        setSrc.append("\t}\n");
        jfgs.setSetInfo(setSrc.toString());

        return jfgs;
    }

    public static void main(String[] args){
        ColumnInfo ci = new ColumnInfo("username", "int", 0);
        JavaFieldGetSet f = createFieldGetSetSRC(ci, new MySqlTypeConvertor());
        System.out.println(f);
    }
}
