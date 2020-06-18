<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/18
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书名</title>
</head>
<body>
<h4>Books Page</h4>
<a href="book.jsp?book=JavaWeb">Java Web</a><br><br>
<a href="book.jsp?book=Java">Java</a><br><br>
<a href="book.jsp?book=Oracle">Oracle</a><br><br>
<a href="book.jsp?book=Ajax">Ajax</a><br><br>
<a href="book.jsp?book=JavaScript">JavaScript</a><br><br>
<a href="book.jsp?book=Android">Android</a><br><br>
<a href="book.jsp?book=Jbpm">Jbpm</a><br><br>
<a href="book.jsp?book=Struts">Struts</a><br><br>
<a href="book.jsp?book=Hibernate">Hibernate</a><br><br>
<a href="book.jsp?book=Spring">Spring</a><br><br>

<br><br>

<%
    // 显示最近浏览的5本书
    // 1. 获取所有的Cookie
    Cookie[] cookies = request.getCookies();
    // 2. 从中筛选出Book的Cookie：如果cookieName为ATGUIGU_BOOK_开头即符合条件
    // 3. 显示cookieValue
    if (cookies != null && cookies.length > 0) {
        for (Cookie c : cookies) {
            String cookieName = c.getName();
            if (cookieName.startsWith("LT_")) {
               out.println(c.getValue());
               out.print("<br>");
            }
        }
    }
%>


</body>
</html>
