<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎界面</title>
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
Hello: <%= request.getParameter("username") %>
<br><br>

<%-- 如果不用session的方式的话，怎么使得重新登录的时候回传用户名 --%>
<%--<a href="login.jsp?username=<%=request.getParameter("username")%>">重新登录</a>--%>

<%-- 这里用session做的话,就不需要在后面添加?username等 --%>
<%
    session.setAttribute("username", request.getParameter("username"));
%>
<%--<a href="login.jsp">重新登录</a>--%>
<a href="<%= response.encodeURL("login.jsp") %>">重新登录</a>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<%--<a href="logout.jsp">注销</a>--%>
<a href="<%= response.encodeURL("logout.jsp") %>">注销</a>

</body>
</html>
