<%@ page import="com.litian.javaweb.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/26
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JavaBean测试</title>
</head>
<body>
<%-- 创建Customer对象给customer --%>
<jsp:useBean id="customer" class="com.litian.javaweb.Customer" scope="session"></jsp:useBean>
<%-- 给属性赋值 --%>
<jsp:setProperty name="customer" property="name" value="LiTian"/>
<%-- 打印效果 --%>
name: <jsp:getProperty name="customer" property="name"/>
<br>

<%-- 根据请求参数的值，为所有属性赋值，省略value --%>
<jsp:setProperty name="customer" property="*"/>
name: <jsp:getProperty name="customer" property="name"/>
<br>
address: <jsp:getProperty name="customer" property="address"/>
<br>
cardType: <jsp:getProperty name="customer" property="cardType"/>
<br>
card: <jsp:getProperty name="customer" property="card"/>
<br>

<%-- 创建Customer对象等同于： --%>

<%--<%--%>
<%--    // 1. 从scope（session）中获取id（customer）属性值，赋给class（com.litian.javaweb.Customer）类型的id变量--%>
<%--    Customer customer2 = (Customer)session.getAttribute("customer");--%>
<%--    // 2. 若属性值为空，则利用反射创建一个新的对象，把该对象赋给id（customer），并以id为属性名放入到scope（session）中。--%>
<%--    if(customer2 == null){--%>
<%--        customer2 = (Customer) Class.forName("com.litian.javaweb.Customer").newInstance();--%>
<%--        session.setAttribute("customer", customer2);--%>
<%--    }--%>
<%--%>--%>

<%-- 赋值操作等同于： --%>
<%--<%--%>
<%--    customer.setName("LiTian");--%>
<%--%>--%>

<%-- 打印操作相当于： --%>
<%--<%= customer.getName() %>--%>

</body>
</html>
