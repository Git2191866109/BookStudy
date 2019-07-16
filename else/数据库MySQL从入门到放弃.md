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
  - -h：主机名
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

  - ifnull函数检测是否为null，如果为null，则返回指定的值，否则返回原本的值：

    ```
    select ifnull(commission_pct, 0) as 奖金率, commission_pct from employees;
    ```
    
  - isnull函数判断某字段或表达式是否为null，如果是，则返回1，否则返回0

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
    
  - 模糊查询
  
    - like
      - 一般和通配符搭配使用，可以判断字符型数值或数值型
      
      - 通配符：
        - % 任意多个字符，包含0个字符
        - _ 任意单个字符
        
      - 查询员工名中包含字符a的员工信息：
      
        ```
        SELECT * FROM employees WHERE last_name LIKE '%a%';
        ```
      
      - 查询员工名中第三个字符为e，第五个字符为a的员工名和工资：
      
        ```
        SELECT last_name, salary FROM employees WHERE last_name LIKE '__n_l%';
        ```
      
      - 查询员工名中第二个字符为`_`的员工名：
      
        ```
        SELECT last_name FROM employees WHERE last_name LIKE '_\_ %';
        ```
      
      - 指定转义字符：
      
        ```
        SELECT last_name FROM employees WHERE last_name LIKE '_$_%' ESCAPE '$';
        ```
      
    - between and
    
      - 使用between and可以提高语句的简洁度；
    
      - 包含临界值；
    
      - 两个临界值不能替换顺序；
    
      - 查询员工编号在100到120之间的员工信息：
    
        ```
        SELECT * FROM employees WHERE employee_id >= 100 AND employee_id <= 120;
        ```
    
        ```
        SELECT * FROM employees WHERE employee_id BETWEEN 100 AND 120;
        ```
    
    - in
    
      - 含义：判断某字段的值是否属于in列表中的某一项
    
      - 使用in提高语句简洁度
    
      - in列表的值类型必须一致或兼容
    
      - in相当于等于，所以不支持通配符（like才支持）
    
      - 查询员工的工种编号是 IT_PROG、AD_VP、AD_PRES中的一个员工名和工种编号：
    
        ```
        SELECT last_name, job_id FROM employees WHERE job_id = 'IT_PROG' OR job_id = 'AD_VP' OR job_id = 'AD_PRES';
        ```
    
        ```
        SELECT last_name, job_id FROM employees WHERE job_id IN ('IT_PROG', 'AD_VP', 'AD_PRES');
        ```
    
    - is null
    
      - 用于判断null值
    
      - =或者<>不能用于判断null值
    
      - 查询没有奖金的员工名和奖金率：
    
        ```
        SELECT
        	last_name,
        	commission_pct
        FROM
        	employees
        WHERE
        	commission_pct IS NULL;
        ```
    
      - 查询有奖金的：
    
        ```
        SELECT
        	last_name,
        	commission_pct
        FROM
        	employees
        WHERE
        	commission_pct IS NOT NULL;
        ```
    
    - 安全等于 <=>
    
      - is null：仅仅可以判断null值，可读性较高
      - <=>：既可以判断null值，又可以判断普通的数值，可读性较低

- 测试题

  - 查询没有奖金，且工资小于18000的salary, last_name：

    ```
    SELECT 
      salary,
      last_name 
    FROM
      employees 
    WHERE commission_pct IS NULL 
      AND salary < 18000;
    ```

  - 查询employees表中，job_id不为‘IT’或者工资为12000的员工信息：

    ```
    SELECT 
      * 
    FROM
      employees 
    WHERE job_id <> 'IT' 
      OR salary = 12000 ;
    ```

  - 查看部门表的结构：

    ```
    DESC departments;
    ```

  - 查询部门表中涉及到了哪些位置编号：

    ```
    SELECT DISTINCT 
      location_id 
    FROM
      departments ;
    ```

  - 经典面试题：`select * from employees;` 和 `select * from employees where commission_pct like ‘%%’ and last_name like ‘%%’;` 结果是否一样？并说明原因：**不一样！如果判断的字段中有null值**，如果查询是`select * from employees where commission_pct like ‘%%’ or last_name like ‘%%’ or ...;`把所有字段都or写齐了就一样了。

- 排序查询

  - 语法：

    select 查询列表

    from 表

    【where 筛选条件】

    order by 排序列表 【asc|desc】

  - asc代表的是升序，desc代表的是降序，如果不写，默认是升序

  - order by子句中可以支持单个字段、多个字段、表达式、函数、别名

  - order by子句一般是放在查询语句的最后面，但limit子句除外

  - 查询员工的信息，要求工资从高到低排序：

    ```
    SELECT 
      * 
    FROM
      employees 
    ORDER BY salary DESC ;
    ```

    从低到高是ASC（默认是ASC）

  - 查询部门编号>=90的员工信息，按入职时间的先后进行排序：

    ```
    SELECT 
      * 
    FROM
      employees 
    WHERE department_id >= 90 
    ORDER BY hiredate ASC ;
    ```

  - 按年薪的高低显示员工的信息和年薪【按表达式（别名）排序】

    ```
    SELECT 
      *,
      salary * 12 * (1+ IFNULL(commission_pct, 0)) AS 年薪 
    FROM
      employees 
    ORDER BY 年薪 DESC ;
    ```

  - 按姓名的长度显示员工的姓名和工资【按函数排序】

    ```
    SELECT 
      LENGTH(last_name) AS 字节长度,
      last_name,
      salary 
    FROM
      employees 
    ORDER BY 字节长度 DESC;
    ```

  - 查询员工信息，要求先按工资排序，再按员工编号排序

    ```
    SELECT 
      * 
    FROM
      employees 
    ORDER BY salary ASC,
      employee_id DESC ;
    ```

- 测试题

  - 查询员工的姓名和部门号和年薪，按年薪降序，按姓名升序

    ```
    SELECT 
      last_name,
      department_id,
      salary * 12 * (1+ IFNULL(commission_pct, 0)) AS 年薪 
    FROM
      employees 
    ORDER BY 年薪 DESC,
      last_name ASC ;
    ```

  - 选择工资不在8000到17000的员工的姓名和工资，按工资降序

    ```
    SELECT 
      last_name,
      salary 
    FROM
      employees 
    WHERE salary NOT BETWEEN 8000 
      AND 17000 
    ORDER BY salary DESC ;
    ```

  - 查询邮箱中包含e的员工信息，并先按邮箱的字节数降序，再按部门号升序

    ```
    SELECT 
      * 
    FROM
      employees 
    WHERE email LIKE '%e%' 
    ORDER BY LENGTH(email) DESC,
      department_id ASC ;
    ```

    





------

我的CSDN：https://blog.csdn.net/qq_21579045

我的博客园：https://www.cnblogs.com/lyjun/

我的Github：https://github.com/TinyHandsome

纸上得来终觉浅，绝知此事要躬行~

欢迎大家过来OB~

by 李英俊小朋友