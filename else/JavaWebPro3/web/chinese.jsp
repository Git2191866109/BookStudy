<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/20
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>中文乱码问题</title>
</head>
<body>
<h4>中文乱码问题</h4>
冲冲冲！
<%
    request.setCharacterEncoding("UTF-8");
%>
<br><br>
username: <%= request.getParameter("username") %>

<%
    String val = request.getParameter("username");
    String username = new String(val.getBytes("iso-8859-1"), "UTF-8");
    out.print(username);
%>

</body>
</html>
