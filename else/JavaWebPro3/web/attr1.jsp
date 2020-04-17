<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/17
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    pageContext.setAttribute("pageContextAttr", "pageContextValue");
    request.setAttribute("requestAttr", "requestValue");
    session.setAttribute("sessionAttr", "sessionValue");
    application.setAttribute("applicationAttr", "applicationValue");
%>
<h2>attr 1 page：<%= new Date() %></h2>
<br><br>
pageContextAttr：<%= pageContext.getAttribute("pageContextAttr")%>
<br><br>
requestAttr：<%= request.getAttribute("requestAttr")%>
<br><br>
sessionAttr：<%= session.getAttribute("sessionAttr")%>
<br><br>
applicationAttr：<%= application.getAttribute("applicationAttr")%>
<br><br>
<a href="attr2.jsp">To attr2 page</a>

</body>
</html>
