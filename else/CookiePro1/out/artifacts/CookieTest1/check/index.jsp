<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/23
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>

<span style="color: red; ">
<%= session.getAttribute("message") == null ? "" : session.getAttribute("message")%>
</span>

<form action="<%= request.getContextPath() %>/checkCodeServlet" method="post">
    name: <input type="text" name="name"/>
    checkCode: <input type="text" name="CHECK_CODE_PARAM_NAME"/>
    <img alt="" src="<%= request.getContextPath() %>/validateColorServlet">
    <input type="submit" name="Submit"/>
</form>
</body>
</html>
