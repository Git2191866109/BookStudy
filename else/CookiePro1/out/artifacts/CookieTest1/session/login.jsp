<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/20
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
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

<%
    Object username = session.getAttribute("username");
    if (username == null) {
        username = "";
    }
%>

<%--<form action="hello.jsp" method="post">--%>
<form action="<%= response.encodeURL("hello.jsp") %>" method="post">
    username: <input type="text" name="username" value="<%= username %>"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
