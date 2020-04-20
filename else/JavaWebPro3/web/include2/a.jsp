<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/19
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>a2</title>
</head>
<body>
<h3>aaa2 page</h3>

<%-- 在a.jsp 中包含b.jsp --%>
<%--<jsp:include page="b.jsp"></jsp:include>--%>

<jsp:forward page="/include2/b.jsp">
    <jsp:param name="username" value="abcd"/>
</jsp:forward>
<%-- 等同于下面 --%>
<%--<%--%>
<%--    request.getRequestDispatcher("/include/b.jsp").forward(request, response);--%>
<%--%>--%>

</body>
</html>
