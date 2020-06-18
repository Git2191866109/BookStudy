<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/18
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书哦</title>
</head>
<body>
<h4>Book Detail Page</h4>
Book: <%= request.getParameter("book") %>
<br><br>

<a href="books.jsp">返回</a>

<%
    String book = request.getParameter("book");

    // 把书的信息以cookie方式传回给浏览器，删除一个Cookie

    // 1. 确定要被删除的Cookie：
    // 前提：ATGUIHU_BOOK_开头的Cookiue数量大于或等于5，
    Cookie[] cookies = request.getCookies();
    // 保存所有的LT_开头的Cookie
    List<Cookie> bookCookies = new ArrayList<Cookie>();
    // 用来保存和books.jsp传入的book匹配的那个Cookie，
    //   传入的book在cookies里面了，就给tmp，这样后面好直接删
    //   如果不在里面，则删除第一个位置的（长度够了的话），长度不够，就不管
    Cookie tmpCookie = null;

    if (cookies != null && cookies.length > 0) {
        for (Cookie c : cookies) {
            String cookieName = c.getName();
            if (cookieName.startsWith("LT_")) {
                bookCookies.add(c);

                if (c.getValue().equals(book)) {
                    tmpCookie = c;
                }

            }
        }
    }
    // 1.1 若从books.jsp页面传入的book不在ATGUIGU_BOOK_的Cookie中，则删除较早的Cookie（数组的第一个Cookie）
    if (bookCookies.size() >= 5 && tmpCookie == null) {
        tmpCookie = bookCookies.get(0);
    }

    // 1.2 若在其中，则删除该Cookie，这里删除通过设置该cookie的maxage为0
    if (tmpCookie != null) {
        tmpCookie.setMaxAge(0);
        ;
        response.addCookie(tmpCookie);
    }

    // 2. 把从books.jsp传入的book作为一个Cookie返回
    Cookie cookie = new Cookie("LT_" + book, book);
    response.addCookie(cookie);
%>


</body>
</html>
