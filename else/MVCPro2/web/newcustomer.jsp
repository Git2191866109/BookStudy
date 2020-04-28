<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/28
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新建用户</title>
</head>
<body>

<%----<%=request.getParameter("name")%>----%>

<%
    Object msg = request.getAttribute("message");
    if (msg != null) {
%>
<br>
<font color="red"><%=msg%></font>
<br>
<%
    }
%>

<form action="add.do" method="post">
    <table>
        <tr>
            <td>CustomerName:</td>
            <td><input type="text" name="name"
                       value="<%=request.getParameter("name") == null ? "": request.getParameter("name")%>"/></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address"
                       value="<%=request.getParameter("address") == null ? "": request.getParameter("address")%>"/></td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td><input type="text" name="phone"
                       value="<%=request.getParameter("phone") == null ? "": request.getParameter("phone")%>"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="确定"></td>
        </tr>
    </table>
</form>
</body>
</html>
