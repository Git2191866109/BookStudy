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

<%
    String tokenValue = new Date().getTime() + "";
    session.setAttribute("token", tokenValue);
%>

<form action="<%= request.getContextPath() %>/tokenServlet" method="post">

    <input type="hidden" name="token" value="<%=tokenValue%>"/>
    <%
        // request.setAttribute("token", "tokenValue");
        System.out.println(session.getAttribute("token"));
    %>


    name: <input type="text" name="name"/>
    <input type="submit" name="Submit"/>
</form>
</body>
</html>
