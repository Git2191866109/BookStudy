<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/18
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>设置Cookie的作用路径</title>
</head>
<body>
<%-- 读取一个name为cookiePath的Cookie --%>
<%
    String cookieValue = null;

    Cookie[] cookies = request.getCookies();
    if (cookies != null && cookies.length > 0) {
        for (Cookie cookie : cookies) {
            if ("cookiePath".equals(cookie.getName())){
                cookieValue = cookie.getValue();
            }
        }
    }
    if(cookieValue != null){
        out.print(cookieValue);
    }else{
        out.print("没有指定的cookie！");
    }
%>
</body>
</html>
