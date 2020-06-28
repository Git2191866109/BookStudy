<%@ page import="com.litian.javaweb.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/28
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL测试</title>
</head>
<body>
<form action="el.jsp" method="post">
    <br>
    用以前的方式写：
    <br>
    username: <input type="text" name="username" value="<%= request.getParameter("username") == null ? "" : request.getParameter("username") %>"/>
    <br>
    用EL写：
    <br>
    username: <input type="text" name="username" value="${param.username}"/>
    <input type="submit" value="Submit"/>
</form>

username: <%= request.getParameter("username") == null ? "" : request.getParameter("username") %>
<br>

<jsp:useBean id="customer" class="com.litian.javaweb.Customer" scope="session"/>
<jsp:setProperty name="customer" property="name" value="憨批"/>

name:
<%
    Customer customer3 = (Customer) session.getAttribute("customer");
    out.print(customer3.getName());
%>
<br>
name: <jsp:getProperty name="customer" property="name"/>
<br>
<%--<a href="el2.jsp">To EL2 Page</a>--%>
<br>
<a href="el2.jsp?score=89">To EL2 Page</a>

</body>
</html>
