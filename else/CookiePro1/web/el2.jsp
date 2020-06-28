<%@ page import="com.litian.javaweb.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/28
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL测试2</title>
</head>
<body>

<%-- 3. EL可以进行自动的类型转换 --%>
score: ${param.score + 11}
<br>
score: <%= request.getParameter("score") + 11 %>
<br>

<%-- 2. EL中的隐含对象 --%>
<%
    Customer cust2 = new Customer();
    cust2.setName("嘻嘻");
    request.setAttribute("customer", cust2);
%>

<%--age: <jsp:getProperty name="customer" property="name"/>--%>
<%--name: ${sessionScope.customer.name}--%>
<%-- 1. EL的 . 或 [] 运算符 --%>
name: ${customer.name}
<br>
name: ${sessionScope.customer["name"]}
</body>
</html>
