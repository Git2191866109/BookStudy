# 数据库MySQL学习笔记

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

### 1. 基础查询

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

### 2. 条件查询

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

### 3. 排序查询

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


### 4. 常见函数

- 功能：类似于java中的方法，将一组逻辑语句
- 好处：

  - 隐藏了实现细节
  - 提高代码的重用性
- 调用：select 函数名(实参列表) 【from 表】;
- 特点：

  - 叫什么（函数名）
  - 干什么（函数功能）
- 分类：

  - 单行函数：如concat、length、ifnull等
  - 分组函数：做统计使用，又称为统计函数、聚合函数、组函数

#### 单行函数

- 字符函数

  - length：获取参数值的字节个数

  - concat：拼接字符串

  - upper/lower：将字符串变成大写/小写

    - 将姓变成大写，名变成小写，然后拼接：

      ```
      SELECT 
        CONCAT(UPPER(last_name), LOWER(first_name)) AS 姓名 
      FROM
        employees ;
      ```

  - substr/substring：截取字符串

    - 注意：索引从1开始

    - 截取从指定索引处后面所有字符

      ```
      SELECT 
        SUBSTR(
          '李莫愁爱上了陆展元',
          6
        ) AS output ;
      ```

    - 截取从指定索引处指定字符长度的字符

      ```
      SELECT 
        SUBSTR(
          '李莫愁爱上了陆展元',
          1,
          3
        ) output ;
      ```

  - 案例：姓名中首字母大写，其他字符小写，然后用_拼接，显示出来：

    ```
    SELECT 
      CONCAT(
        UPPER(SUBSTR(last_name, 1, 1)),
        '_',
        LOWER(SUBSTR(last_name, 2))
      ) AS output 
    FROM
      employees ;
    ```

  - instr：返回子串第一次出现的索引，如果找不到返回0

    ```
    SELECT 
      INSTR(
        '杨不悔爱上了殷六侠',
        '殷六侠'
      ) AS output ;
    ```

  - trim：去掉字符串前后的空格或子串

    ```
    SELECT 
      LENGTH(TRIM('   张翠山   ')) AS output ;
    ```

    ```
    SELECT 
      TRIM('a' FROM 'aaa张a翠aa山aaaaa') AS output ;
    ```

  - lpad：用指定的字符实现左填充指定长度

  - rpad：用指定的字符实现右填充指定长度

  - replace：替换，替换所有的子串

- 数学函数

  - round：四舍五入
  - ceil：向上取整，返回>=该参数的最小整数
  - floor：向下取整，返回<=该参数的最大整数
  - truncate：截断，小数点后截断到几位
  - mod：取余，被除数为正，则为正；被除数为负，则为负
  - rand：获取随机数，返回0-1之间的小数

- 日期函数

  - now：返回当前系统日期+时间

  - curdate：返回当前系统日期，不包含时间

  - curtime：返回当前时间，不包含日期

  - 可以获取指定的部分，年、月、日、小时、分钟、秒

    ```
    SELECT 
      YEAR(hiredate) 年 
    FROM
      employees ;
    ```

  - str_to_date：将日期格式的字符转换成指定格式的日期

    ```
    SELECT 
      STR_TO_DATE('1998-3-2', '%Y-%c-%d') AS output ;
    ```

    - 查询入职日期为1992-4-3的员工信息

      ```
      SELECT 
        * 
      FROM
        employees 
      WHERE hiredate = STR_TO_DATE('4-3 1992', '%c-%d %Y') ;
      ```

  - date_format：将日期转换成字符串

    ```
    SELECT 
      DATE_FORMAT(NOW(), '%y年%m月%d日)') AS output ;
    ```

    - 查询有奖金的员工名和入职日期（xx月/xx日 xx年）

      ```
      SELECT 
        last_name,
        DATE_FORMAT(hiredate, '%m月/%d日 %y年') AS 入职日期 
      FROM
        employees 
      WHERE commission_pct IS NOT NULL ;
      ```
    
  - datediff：返回两个日期相差的天数

  - monthname：以英文形式返回月

- 其他函数

  ```
  SELECT VERSION();	当前数据库服务器的版本
  SELECT DATABASE();	当前打开的数据库
  SELECT USER();		当前用户
  password('字符');		返回该字符的密码形式
  md5('字符');			也是加密的一种形式（MD5）
  ```

- 流程控制函数

  - if函数：if else的效果

    ```
    SELECT 
      last_name,
      commission_pct,
      IF(
        commission_pct IS NULL,
        '没奖金，呵呵',
        '有奖金，嘻嘻'
      ) 备注 
    FROM
      employees ;
    ```

  - case函数的使用1：switch case的效果

    - 语法：

      ```
      case 要判断的字段或表达式
      when 常量1 then 要显示的值1或语句1;
      when 常量2 then 要显示的值2或语句2;
      ...
      else 要显示的值n或语句n;
      end
      ```

    - 查询员工的工资，要求：

      部门号=30，显示的工资为1.1倍

      部门号=40，显示的工资为1.2倍

      部门号=50，显示的工资为1.3倍

      其他部门，显示的工资为原工资

      ```
      SELECT 
        salary AS 原始工资,
        department_id,
        CASE
          department_id 
          WHEN 30 
          THEN salary * 1.1 
          WHEN 40 
          THEN salary * 1.2 
          WHEN 50 
          THEN salary * 1.3 
          ELSE salary 
        END AS 新工资 
      FROM
        employees ;
      ```

  - case函数的使用2：类似于多重if

    ```
    case
    when 条件1 then 要显示的值1或语句1
    when 条件2 then 要显示的值2或语句2
    ...
    else 要显示的值n或语句n
    end
    ```

    - 查询员工的工资情况

      如果工资>20000，显示A级别

      如果工资>15000，显示B级别

      如果工资>10000，显示C级别

      否则，显示D级别

      ```
      SELECT 
        salary,
        CASE
          WHEN salary > 20000 
          THEN 'A' 
          WHEN salary > 15000 
          THEN 'B' 
          WHEN salary > 10000 
          THEN 'C' 
          ELSE 'D' 
        END AS 工资级别 
      FROM
        employees ;
      ```
  
- 测试题

  - 显示系统时间（日期+时间）

    ```
    SELECT NOW();
    ```

  - 查询员工号，姓名，工资，以及工资提高20%后的结果（new salary）

    ```
    SELECT 
      employee_id,
      last_name,
      salary,
      salary * 1.2 AS "new salary" 
    FROM
      employees ;
    ```

  - 将员工的姓名按首字母排序，并写出姓名的长度（length）

    ```
    SELECT 
      last_name,
      LENGTH(last_name) 
    FROM
      employees 
    ORDER BY SUBSTR(last_name, 1, 1) ;
    ```

  - 做一个查询

    ```
    SELECT 
      CONCAT(
        last_name,
        ' earns ',
        salary,
        ' monthly but wants ',
        salary * 3
      ) AS "Dream Salary" 
    FROM
      employees ;
    ```

  - case-when训练

    ```
    SELECT 
      last_name,
      job_id AS job,
      CASE
        job_id 
        WHEN 'AD_PRES' 
        THEN 'A' 
        WHEN 'ST_MAN' 
        THEN 'B' 
        WHEN 'IT_PROG' 
        THEN 'C' 
        WHEN 'SA_PRE' 
        THEN 'D' 
        WHEN 'ST_CLERK' 
        THEN 'E' 
      END AS Grade 
    FROM
      employees 
    WHERE job_id = 'AD_PRES' ;
    ```

#### 分组函数

- 功能：用作统计使用，又称为聚合函数或统计函数或组函数

- 分类：sum 求和、avg 平均值、max 最大值、min 最小值、count 计数（非空）

  ```
  SELECT SUM(salary) FROM employees;
  ```

- 特点

  - sum、avg一般用于处理数值型数据
  - max、min、count可以处理任何类型数据
  - 以上分组函数都忽略null值

- 可以和distinct搭配实现去重的运算

  ```
  SELECT 
    SUM(DISTINCT salary),
    SUM(salary) 
  FROM
    employees ;
  ```

  ```
  SELECT 
    COUNT(DISTINCT salary),
    COUNT(salary) 
  FROM
    employees ;
  ```

- count函数的单独介绍

  - 效率

    - MYISAM存储引擎下，count(*)的效率高
    - INNODB存储引擎下，count(*)和count(1)效率差不多，比count(字段)要高一些

  - 使用count(*) 统计一共有多少行

    ```
    SELECT COUNT(salary) FROM employees;
    SELECT COUNT(*) FROM employees;
    SELECT COUNT(1) FROM employees;
    ```

- 和分组函数一同查询的字段有限制，要求是group by后的字段

- 训练题

  - 查询公司员工工资的最大值，最小值，平均值，总和

    ```
    SELECT 
      MAX(salary),
      MIN(salary),
      AVG(salary),
      SUM(salary) 
    FROM
      employees ;
    ```

  - 查询员工表中的最大入职时间和最小入职时间的相差天数（difference）

    ```
    SELECT 
      DATEDIFF(MAX(hiredate), MIN(hiredate)) DIFFERENCE 
    FROM
      employees ;
    ```

  - 查询部门编号为90的员工个数

    ```
    SELECT 
      COUNT(*) 
    FROM
      employees 
    WHERE department_id = 90 ;
    ```

### 5. 分组查询

- 语法：

  select 分组函数，列（要求出现在group by的后面）

  from 表

  【where 筛选条件】

  group by 分组的列表

  【having 分组后的筛选】

  【order by 子句】

- 注意：查询列表比较特殊，要求是分组函数和group by后出现的字段

- 特点：

  - 分组查询中的筛选条件分为两类：

    ​						数据源					位置								关键字

    分组前筛选	  原始表					group by子句的前面		where

    分组后筛选	  分组后的结果集	  group by子句的后面		having

  - 分组函数做条件肯定是放在having子句中

  - 能用分组前筛选的，就优先考虑使用分组前筛选

  - group by子句支持单个字段分组，多个字段分组（多个字段之间用逗号隔开没有顺序要求），表达式或函数（用得较少）

  - 也可以添加排序（排序放在整个分组查询最后位置）

- 查询每个工种的最高工资

  ```
  SELECT 
    MAX(salary),
    job_id 
  FROM
    employees 
  GROUP BY job_id ;
  ```

- 查询每个位置上的部门个数

  ```
  SELECT 
    COUNT(*),
    location_id 
  FROM
    departments
  GROUP BY location_id ;
  ```

- 查询邮箱中包含a字符的，每个部门的平均工资

  ```
  SELECT 
    AVG(salary),
    department_id 
  FROM
    employees 
  WHERE email LIKE '%a%' 
  GROUP BY department_id ;
  ```

- 查询有奖金的每个领导手下员工的最高工资

  ```
  SELECT 
    MAX(salary),
    manager_id 
  FROM
    employees 
  WHERE commission_pct IS NOT NULL 
  GROUP BY manager_id ;
  ```

- 查询那个部门的员工个数>2

  - 查询每个部门的员工个数

    ```
    SELECT 
      COUNT(*) AS 员工个数,
      department_id 
    FROM
      employees 
    GROUP BY department_id ;
    ```

  - 根据上面的结果进行筛选，查询哪个部门的员工个数＞2

    ```
    SELECT 
      COUNT(*) AS 员工个数,
      department_id 
    FROM
      employees 
    GROUP BY department_id 
    HAVING 员工个数 > 2 ;
    ```

- 添加分组后的筛选用having，分组前的用where

- 查询每个工种有奖金的员工的最高工资>12000的工种编号和最高工资

  - 查询每个工种有奖金的员工的最高工资

    ```
    SELECT 
      MAX(salary),
      job_id 
    FROM
      employees 
    WHERE commission_pct IS NOT NULL
    GROUP BY job_id ;
    ```

  - 根据上面的结果继续筛选，最高工资>12000

    ```
    SELECT 
      MAX(salary) AS 最高工资,
      job_id 
    FROM
      employees 
    WHERE commission_pct IS NOT NULL 
    GROUP BY job_id 
    HAVING 最高工资 > 12000 ;
    ```

  - 查询领导编号>102的每个领导手下的最低工资>5000的领导编号是哪个，以及其最低工资

    ```
    SELECT 
      MIN(salary) AS 最低工资,
      manager_id 
    FROM
      employees 
    WHERE manager_id > 102 
    GROUP BY manager_id 
    HAVING 最低工资 > 5000 ;
    ```

  - 按表达式或函数分组

    - 按员工姓名的长度分组，查询每一组的员工个数，筛选员工个数>5的有哪些

      - 查询每个长度的员工个数

        ```
        SELECT 
          COUNT(*) 员工个数,
          LENGTH(last_name) 姓名长度 
        FROM
          employees 
        GROUP BY 姓名长度 ;
        ```

      - 添加筛选条件

        ```
        SELECT 
          COUNT(*) 员工个数,
          LENGTH(last_name) 姓名长度 
        FROM
          employees 
        GROUP BY 姓名长度 
        HAVING 员工个数 > 5 ;
        ```

  - 按多个字段分组

    - 查询每个部门每个工种的员工的平均工资

      ```
      SELECT 
        AVG(salary),
        department_id,
        job_id 
      FROM
        employees 
      GROUP BY department_id,
        job_id ;
      ```

  - 添加排序

    - 查询每个部门每个工种的员工的平均工资，并按平均工资的高低显示

      ```
      SELECT 
        AVG(salary) AS 平均工资,
        department_id,
        job_id 
      FROM
        employees 
      GROUP BY department_id,
        job_id 
      ORDER BY 平均工资 DESC ;
      ```

- 练习题

  - 查询各job_id的员工工资的最大值、最小值、平均值，总和，并按job_id升序

    ```
    SELECT 
      MAX(salary),
      MIN(salary),
      AVG(salary),
      SUM(salary),
      job_id 
    FROM
      employees 
    GROUP BY job_id 
    ORDER BY job_id ;
    ```

  - 查询员工最高工资和最低工资的差距（DIFFERENCE）

    ```
    SELECT 
      MAX(salary) - MIN(salary) AS DIFFERENCE 
    FROM
      employees ;
    ```

  - 查询各个管理者手下员工的最低工资，其中最低工资不能低于6000，没有管理者的员工不计算在内

    ```
    SELECT 
      MIN(salary) AS 最低工资 
    FROM
      employees 
    WHERE manager_id IS NOT NULL 
    GROUP BY manager_id 
    HAVING 最低工资 >= 6000 ;
    ```

  - 查询所有部门的编号，员工数量和工资平均值，并按平均工资降序

    ```
    SELECT 
      department_id,
      COUNT(*) AS 员工数量,
      AVG(salary) AS 工资平均值 
    FROM
      employees 
    GROUP BY department_id 
    ORDER BY 工资平均值 DESC ;
    ```

  - 查询具有各个job_id的员工人数

    ```
    SELECT 
      COUNT(*),
      job_id 
    FROM
      employees 
    GROUP BY job_id ;
    ```

### 6. 连接查询

- 含义：又称多表查询，当查询的字段来自于多个表时，就会用到连接查询

- 笛卡尔乘积现象：表1有m行，表2有n行，结果=m*n

  - 发生原因：没有有效的连接条件
  - 如何避免：添加有效的连接条件

- 分类：

  - 按年代分类：l
    - sql92标准：仅仅支持内连接
    - sql99标准【推荐】：支持内连接+外连接（左外和右外）+交叉连接
  - 按功能分类：
    - 内连接
      - 等值连接
      - 非等值连接
      - 自连接
    - 外连接
      - 左外连接
      - 右外连接
      - 全外连接（mysql不支持）
    - 交叉连接

- sql92标准

  - 等值连接

    - 多表等值连接的结果为多表的交集部分

    - n表连接，至少需要n-1个连接条件

    - 多表的顺序没有要求

    - 一般需要为表起别名

    - 可以搭配前面介绍的所有子句使用，比如排序、分组、筛选

    - 查询女神名和对应的男神名：

      ```
      SELECT 
        NAME,
        boyname 
      FROM
        boys,
        beauty 
      WHERE beauty.boyfriend_id = boys.id ;
      ```

    - 查询员工名和对应的部门名

      ```
      SELECT 
        last_name,
        department_name 
      FROM
        employees,
        departments 
      WHERE employees.`department_id` = departments.`department_id` ;
      ```

  - 为表起别名

    - 提高语句的简洁度

    - 区分多个重名的字段

    - 注意：如果为表起了别名，则查询 的字段就不能使用原始的表明去限定

    - 查询员工名、工种号、工种名

      ```
      SELECT 
        last_name,
        e.`job_id`,
        job_title 
      FROM
        employees e,
        jobs j 
      WHERE e.`job_id` = j.`job_id` ;
      ```

  - 两个表的顺序是否可以调换

    - 查询员工名、工种号、工种名

      ```
      SELECT 
        last_name,
        e.`job_id`,
        job_title 
      FROM
        jobs j ,
        employees e
      WHERE e.`job_id` = j.`job_id` ;
      ```

  - 可以加筛选

    - 查询有奖金的员工名、部门名

      ```
      SELECT 
        last_name,
        department_name 
      FROM
        employees AS e,
        departments AS d 
      WHERE e.`department_id` = d.`department_id` 
        AND e.`commission_pct` IS NOT NULL ;
      ```

    - 查询城市名中第二个字符为o的部门名和城市名

      ```
      SELECT 
        department_name,
        city 
      FROM
        departments d,
        locations l 
      WHERE d.`location_id` = l.`location_id` 
        AND city LIKE '_o%' ;
      ```

  - 可以加分组

    - 查询每个城市的部门个数

      ```
      SELECT 
        COUNT(*) 个数,
        city 
      FROM
        departments d,
        locations l 
      WHERE d.`location_id` = l.`location_id` 
      GROUP BY city ;
      ```

    - 查询有将近的每个部门的部门名和部门的领导编号和该部门的最低工资

      ```
      SELECT 
        department_name,
        d.manager_id,
        MIN(salary) 
      FROM
        departments d,
        employees e 
      WHERE d.`department_id` = e.`department_id` 
        AND commission_pct IS NOT NULL 
      GROUP BY department_name,
        d.manager_id ;
      ```

  - 可以加排序

    - 查询每个工种的工种名和员工的个数，并且按员工个数降序

      ```
      SELECT 
        job_title,
        COUNT(*) AS 个数 
      FROM
        employees e,
        jobs j 
      WHERE e.`job_id` = j.`job_id` 
      GROUP BY job_title 
      ORDER BY 个数 DESC ;
      ```

  - 可是实现三表连接：

    - 查询员工名、部门名和所在的城市

      ```
      SELECT 
        last_name,
        department_name,
        city 
      FROM
        employees e,
        departments d,
        locations l 
      WHERE e.`department_id` = d.`department_id` 
        AND d.`location_id` = l.`location_id` ;
      ```

  - 非等值连接
  
    - 查询员工的工资和工资级别
  
      ```
      SELECT 
        salary,
        grade_level 
      FROM
        employees e,
        job_grades g 
      WHERE salary BETWEEN g.lowest_sal 
        AND g.highest_sal ;
      ```
  
  - 自连接
  
    - 查询 员工名和上级的名称
  
      ```
      SELECT 
        e.employee_id,
        e.last_name,
        m.employee_id,
        m.last_name 
      FROM
        employees e,
        employees m 
      WHERE e.`manager_id` = m.`employee_id` ;
      ```
  
  - 测试题：
  
    - 显示员工表的最大工资，工资平均值
  
      ```
      SELECT 
        MAX(salary),
        AVG(salary) 
      FROM
        employees ;
      ```
  
    - 查询员工表的employee_id，job_id，last_name，按department_id降序，salary升序
  
      ```
      SELECT 
        employee_id,
        job_id,
        last_name 
      FROM
        employees 
      ORDER BY department_id DESC,
        salary ASC ;
      ```
  
    - 查询员工表的job_id中包含a和e的，并且a在e的前面
  
      ```
      SELECT 
        job_id 
      FROM
        employees 
      WHERE job_id LIKE '%a%e%' ;
      ```
  
    - 显示当前日期，以及去前后空格，截取子字符串的函数
  
      ```
      select now();
      select trim();
      select substr(str, startIndex, [length])
      ```
  
- sql99语法

  - 语法：

    select 查询列表

    from 表1 别名 【连接类型】

    join 表2 别名 

    on 连接条件

    【where 筛选条件】

    【group by 分组】

    【having 筛选条件】

    【order by 排序列表】

  - 内连接（同上）：连接类型是inner

  - 外连接

    - 左外：left 【outer】
    - 右外：right【outer】
    - 全外：full 【outer】

  - 交叉连接：cross

  - 内连接：

    - 语法：

      select 查询列表

      from 表1 别名

      inner join 表2 别名

      on 连接条件

      …

    - 分类：

      等值连接

      非等值连接

      自连接

    - 特点：

      - 添加排序、分组、筛选
      - inner可以省略
      - 筛选条件放在where后面，连接条件放在on后面，提高分离性，便于阅读
      - inner join连接和sql92语法中的等值连接效果是一样的，都是查询多表的交集

    - 等值连接：

      - 查询员工名、部门名

        ```
        SELECT 
          last_name,
          department_name 
        FROM
          employees e 
          INNER JOIN departments d 
            ON e.`department_id` = d.`department_id` ;
        ```

      - 查询名字中包含e的给员工名和工种名

        ```
        SELECT 
          last_name,
          job_title 
        FROM
          employees e 
          INNER JOIN jobs j 
            ON e.`job_id` = j.`job_id` 
        WHERE last_name LIKE "%e%" ;
        ```

      - 查询部门个数>3的城市名和部门个数

        ```
        SELECT 
          city,
          COUNT(*) 部门个数 
        FROM
          departments d 
          INNER JOIN locations l 
            ON d.`location_id` = l.`location_id` 
        GROUP BY city 
        HAVING 部门个数 > 3 ;
        ```

      -  查询哪个部门的部门员工个数>3的部门名和员工个数，并按个数降序排序

        ```
        SELECT 
          department_name,
          COUNT(*) 员工个数 
        FROM
          departments d 
          INNER JOIN employees e 
            ON d.`department_id` = e.`department_id` 
        GROUP BY d.`department_id` 
        HAVING 员工个数 > 3 
        ORDER BY 员工个数 DESC ;
        ```

      - 查询员工名、部门名、工种名，并按部门名降序

        ```
        SELECT 
          last_name,
          department_name,
          job_title 
        FROM
          employees e 
          INNER JOIN departments d 
            ON e.`department_id` = d.`department_id` 
          INNER JOIN jobs j 
            ON e.`job_id` = j.`job_id` 
        ORDER BY d.`department_id` DESC ;
        ```

    - 非等值连接

      - 











------

我的CSDN：https://blog.csdn.net/qq_21579045

我的博客园：https://www.cnblogs.com/lyjun/

我的Github：https://github.com/TinyHandsome

纸上得来终觉浅，绝知此事要躬行~

欢迎大家过来OB~

by 李英俊小朋友