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

score: ${param.score + 11}
<br>
score: <%= request.getParameter("score") + 11 %>
<br>

<%
    Customer cust2 = new Customer();
    cust2.setName("嘻嘻");
    request.setAttribute("customer", cust2);
%>

<%--age: <jsp:getProperty name="customer" property="name"/>--%>
<%--name: ${sessionScope.customer.name}--%>
name: ${customer.name}
<br>
name: ${sessionScope.customer["name"]}
</body>
</html>
