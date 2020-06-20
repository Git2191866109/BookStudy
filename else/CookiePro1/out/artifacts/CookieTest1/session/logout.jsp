<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/20
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注销</title>
</head>
<body>
Session ID: <%= session.getId() %>
<br><br>
isNew: <%= session.isNew() %>
<br><br>
MaxInactiveInternal: <%= session.getMaxInactiveInterval() %>
<br><br>
CreateTime: <%= session.getCreationTime() %>
<br><br>
LastAccessTime: <%= session.getLastAccessedTime() %>
<br><br>
Bye: <%= session.getAttribute("username") %>
<br><br>

<a href="login.jsp">重新登录</a>

<%
    session.invalidate();
%>

</body>
</html>
