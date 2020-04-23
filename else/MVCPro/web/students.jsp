<%@ page import="com.litian.mvc.Student" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/21
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
<%--
<%=
request.getAttribute("lists")
%>
<br><br>
<%
    List<String> names = (List) request.getAttribute("lists");
    for (String name : names) {
        out.print(name + "<br>");
    }
%>
--%>

<%
    List<Student> stus = (List<Student>) request.getAttribute("students");
%>

<table border="1" cellpadding="10", cellspacing="0">
    <tr>
        <th>id</th>
        <th>user</th>
        <th>password</th>
        <th>Delete</th>
    </tr>
    <%
        for (Student st : stus) {
    %>
    <tr>
        <td><%= st.getId() %></td>
        <td><%= st.getUser() %></td>
        <td><%= st.getPassword() %></td>
        <td><a href="deleteStudent?id=<%=st.getId()%>">Delete</a></td>
    </tr>

    <%
        }
    %>
</table>
</body>
</html>
