<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/2
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" session="false" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    // 在javaweb规范中使用Cookie类代表cookie
    // 1. 获取Cookie
    Cookie[] cs = request.getCookies();
    if (cs != null && cs.length > 0) {
        for (Cookie cc : cs) {
            // 2. 获取cookie的名字和值
            out.print(cc.getName() + ": " + cc.getValue());
            out.print("<br>");
        }
    } else {
        // 若没有cookie，则创建一个返回
        out.print("没有一个cookie，正在创建并返回...");
        // 1. 创建一个Cookie对象
        Cookie cookie = new Cookie("name", "liyingjun");
        // 设置cookie的最大时效，以秒为单位，若为0，表示立即删除该cookie
        // 若为负数，表示不存储该cookie，若为正数，表示该cookie的存储时间。
        cookie.setMaxAge(30);

        // 2. 调用response的一个方法，把cookie传给客户端
        response.addCookie(cookie);
    }
%>

</body>
</html>
