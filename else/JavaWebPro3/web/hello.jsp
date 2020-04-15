<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/15
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>JSP给我冲！</title>
</head>
<body>
<%
    Date date = new Date();
    System.out.println(date);
%>

<%
    String str = date.toString();

    // 没有声明就可以使用的对象称为隐含对象

    // 1. **request**：HttpServletRequest对象
    String name = request.getParameter("name");
    System.out.println("1 --> " + name);

    // 2. **response**：HttpServletResponse对象(在jsp页面几乎用不到)
    Class clazz = response.getClass();
    System.out.println("2 --> " + clazz);
    System.out.println(response instanceof HttpServletResponse);

    // 3. **pageContext**：页面上下文，是pageContext的一个对象，可以从该对象中获取其他对象信息
    ServletRequest hsr = pageContext.getRequest();
    System.out.print("3 --> ");
    System.out.println(hsr == request);

    // 4. **application**：代表当前web应用，来源于javax.servlet.servletcontext
    System.out.println(application.getInitParameter("password"));

    // 5. **config**：来源于ServletConfig，它包含了当前JSP/Servlet所在的WEB应用的配置信息，若需要访问当前jsp配置的初始化参数，需要通过映射才可以
    System.out.println(config.getInitParameter("test"));

    // 6. **out**： JspWriter对象，调用out.println()直接把字符串打印到浏览器上
    out.println(request.getParameter("name"));
    // 换行方法1
%>
<br>
<%
    // 换行方法2
    out.println("<br>");
    out.println(application.getInitParameter("password"));

    // 7. **session**：来源于javax.servlet.http.HttpSession。代表浏览器和服务器的一次会话，它用于存储客户端请求的信息，因此它是有状态交互式的。
    // 8. **page**：指向当前jsp对应的Servlet对象的引用，但为Object类型，只能调用Object类方法（几乎不使用）
    out.println("<br>");
    out.println(this);
    out.println("<br>");
    out.println(page);
    // 9. **exception**：用于捕获JSP抛出的异常。它只有在JSP页面属性isErrorPage=true时才可用。

%>
</body>
</html>
