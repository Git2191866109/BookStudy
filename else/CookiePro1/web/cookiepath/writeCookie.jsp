<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/18
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>写入Cookie</title>
</head>
<body>
<%-- 向客户端浏览器写入一个Cookie: cookiePath, cookiePathValue --%>
<%
    Cookie cookie = new Cookie("cookiePath", "CookiePathValue");
    // 设置Cookie的作用范围，然后就可读到上一目录的cookie，
    // 下一行代码是指，将cookie的作用范围设置为web应用的根目录
    cookie.setPath(request.getContextPath());
    response.addCookie(cookie);

    // Cookie的作用范围：可以作用当前目录和当前目录的子目录，
    // 但不能作用于当前目录的上一级目录。
    // 可以通过setPath的方式来设置Cookie的作用范围，其中/代表站点的根目录
%>

<a href="../cookie2.jsp">To Read Cookie</a>
<br><br>
<%= request.getContextPath() %>

</body>
</html>
