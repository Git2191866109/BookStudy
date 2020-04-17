<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/17
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Date date = new Date(System.currentTimeMillis());
    out.println(date);
%>

<br>

<%= date %>

<%
    String ageStr = request.getParameter("age");
    Integer age = Integer.parseInt(ageStr);

    if (age >= 18) {
%>
成人...
<%
    } else {
        out.print("未成年人！");
    }
%>
<%!
    void test(){}
%>
<%-- 这是注释 --%>

</body>
</html>
