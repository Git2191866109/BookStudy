<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/19
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Session测试</title>
</head>
<body>
    <%= session.getId() %>

    <%
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(20);
        response.addCookie(cookie);
    %>
</body>
</html>
