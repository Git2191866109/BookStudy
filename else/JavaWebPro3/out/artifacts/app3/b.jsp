<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/17
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>b</title>
</head>
<body>
<h4>b page</h4>

<%
    // 1. 请求转发的代码
    // request.getRequestDispatcher("c.jsp").forward(request, response);
    // 2. 请求的重定向
    response.sendRedirect("c.jsp");
%>
</body>
</html>
