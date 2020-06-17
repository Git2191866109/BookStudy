<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/2
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%
    // 若可以获取到请求参数name，则打印出欢迎信息。把登录信息存储到Cookie中，并设置Cookie的最大时效为30s
    String name = request.getParameter("name");
    if (name != null && !name.trim().equals("")) {
        Cookie cookie = new Cookie("name", name);
        cookie.setMaxAge(30);
        response.addCookie(cookie);
    } else {
        // 这里直接刷新就没有参数name，所以去寻找cookie的信息了
        // 从Cookie中读取用户信息，若存在则打印欢迎信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cookiename = cookie.getName();
                if ("name".equals(cookiename)) {
                    String val = cookie.getValue();
                    name = val;
                }
            }
        }
    }

    // 这里看cookie和获取的name是否有一个方法能搞到信息
    if (name != null && !name.trim().equals("")) {
        out.print("Hello: " + name);
    }else{
        // 若既没有请求参数，也没有Cookie，则重定向到login.jsp
        response.sendRedirect("login.jsp");
    }

%>
</body>
</html>
