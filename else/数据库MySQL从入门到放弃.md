#  数据库MySQL学习笔记

[TOC]

## 写在前面

学习链接：[数据库 MySQL 视频教程全集](https://www.bilibili.com/video/av47702905)

## 数据库的好处

1. 持久化数据到本地
2. 可以实现结构化查询，方便管理

## 数据库的相关概念

- DB：数据库（database）：存储数据的“仓库”，它保存了一系列有组织的数据。
- DBMS：数据库管理系统（Database Management System）。数据库是通过DBMS创建和操作的容器。
- SQL：结构化查询语言（Structure Query Language），专门用来与数据库通信的语言。
  - SQL优点：
  - 不是某个特定数据库供应商专有的语言，几乎所有DBMS都支持SQL
  - 简单易学
  - 实际上强有力的语言，灵活使用可以进行非常复杂和高级的数据库操作

## 数据库存储数据的特点

- 将数据放到表中，表再放到库中
- 一个数据库中可以有多个表，每个表都有一个的名字，用来标识自己。表名具有唯一性。
- 表具有一些特性，这些特性定义了数据在表中如何存储，类似java中 “类”的设计。 
- 表由列组成，我们也称为字段。所有表都是由一个或多个列
  组成的，每一列类似java 中的“属性” 。
- 表中的数据是按行存储的，每一行类似于java中的“对象”。 
- DBMS分为两类：
  - 基于共享文件系统的DBMS（ACCESS）
  - 基于客户机——服务器的DBMS（MySQL、Oracle、SqlServer）

## MySQL服务的启动和停止

- 停止服务：net stop mysql
- 开启服务：net start mysql

## MySQL服务端的登录和退出

- 登录：mysql 【-h  localhost -P 3306】（本机可省略） -u root -p（可以直接写密码，不能有空格）
  - -h：host
  - -P：端口号
  - -u：用户名
  - -p：密码
- 退出：exit
- 查看mysql数据库的版本：
  - select version();（mysql命令）
  - mysql –version（dos命令）

## MySQL的常用命令

- 查看当前所有的数据库：show databases;

- 打开指定的库：use 库名

- 查看当前的所有表：show tables;

- 查看其他库的所有表：show tables from 库名;

- 创建表：

  create table 表名(

  ​	列名 列类型,

  ​	列名 列类型,

  ​	…

  );

- 查看表结构：desc 表名;

## MySQL语法规范

1. 不区分大小写，建议关键字大写，表名、列名小写
2. 每句话用;或\g结尾 
3. 每条命令根据需要，各子句一般分行写，关键字不能缩写也不能分行 
4. 注释
   - 单行注释：#注释文字
   - 单行注释：-- 注释文字（要有空格）
   - 多行注释：/* 注释文字 */

## DQL（Data Query Language）数据查询语言

- 基础查询

  - 语法：

    select 查询列表

    from 表名;

  - 特点：

    - 查询列表可以是：表中的字段、常量、表达式、函数
    - 查询的结果是一个虚拟的表格

  - **注意：在进行查询操作之前要指定所有的库**：use myemployees;

  - 查询表中的单个字段：select last_name from employees;

  - 查询表中的多个字段：select last_name, salary, email from employees;

  - 查询表中的所有字段：select * from employees;

  - **按F12进行格式化**

  - 着重号`用来区分是否是关键字或者字段

  - 选中语句进行执行或F9

  - 查询常量值：

    select 100;

    select ‘john’;

  - 查询表达式：select 100*98；

  - 查询函数：select version();

  - 起别名：

    - 便于理解
    - 如果查询的字段有重名的情况，使用别名可以区分开来

    方式1：

    select 100%98 as 结果;

    select last_name as 姓, first_name as 名 from employees;

    方式2：

    select last_name 姓, first_name 名 from employees;

    如果别名有特殊符号要加双引号：

    select salary as "out put" from employees;

  - 去重：

    查询员工表中涉及到的所有部门编号：select distinct department_id from employees;

  - +号的作用：

    - 两个操作数为数值型，则做加法运算
    - 只要其中一方为字符型，试图将字符型数值转换成数值型，如果转换成功，则继续做加法运算；如果转换失败，则将字符型数值转换成0
    - 只要其中一方为null，则结果肯定为null

  - 使用concat连接字符串：

    查询员工的名和姓连接成一个字段，并显示为姓名：select concat(last_name,first_name) as 姓名 from employees;

  - ifnull函数检测是否为空：

    select ifnull(commission_pct, 0) as 奖金率, commission_pct from employees;

- 条件查询

  - 语法：select 查询列表 from 表明 where 筛选条件;

  - 分类：

    - 按条件表达式筛选：

      - 条件运算符：> < = != <> >= <=

    - 按逻辑表达式筛选：

      - 主要作用：用于连接条件表达式
      - 逻辑运算符：&& || ! and or not

    - 模糊查询

      like

      between and

      in

      is null

  - 按条件表达式筛选：

    - 查询工资>12000的员工信息：select * from employees where salary>12000;
    - 查询部门编号不等于90号的员工名和部门编号：select last_name, department_id from employees where department_id != 90;

  - 按逻辑表达式筛选：

    - 查询工资在10000到20000之间的员工名、工资以及奖金：select last_name, salary, commission_pct from employees where salary >= 10000 and salary <= 20000;
    - 查询部门编号不是在90到110之间，或者工资高于15000的员工信息：select * from employees where department_id < 90 or department_id > 110 or salary > 15000;









------

我的CSDN：https://blog.csdn.net/qq_21579045

我的博客园：https://www.cnblogs.com/lyjun/

我的Github：https://github.com/TinyHandsome

纸上得来终觉浅，绝知此事要躬行~

欢迎大家过来OB~

by 李英俊小朋友