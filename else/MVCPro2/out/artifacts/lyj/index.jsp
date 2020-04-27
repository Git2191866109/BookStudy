<%@ page import="com.litian.mvc.domain.Customer" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/23
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <script type="text/javascript" src="scripts/jquery-3.5.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".delete").click(function () {
                var content = $(this).parent().parent().find("td:eq(1)").text().replace('\n', '').trim();
                return confirm("确定要删除【" + content + "】的信息吗？");
                // return confirm("aaa" + content + "end");
            });
        })
    </script>
</head>
<body>
<form action="query.do" method="post">
    <table>
        <tr>
            <td>CustomerName:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td><input type="text" name="name"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Query"></td>
            <td><a href="">Add New Customer</a></td>
        </tr>
    </table>
</form>
<br><br>

<%
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    if (customers != null && customers.size() > 0) {
%>
<br>
<br>
<table border="1" cellspacing="0" cellpadding="10">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Address</th>
        <th>Phone</th>
        <th>UPDATE/DELETE</th>
    </tr>

    <%
        for (Customer customer : customers) {
    %>

    <tr>
        <td><%=customer.getId()%>
        </td>
        <td><%=customer.getName()%>
        </td>
        <td><%=customer.getAddress()%>
        </td>
        <td><%=customer.getPhone()%>
        </td>
        <td><a href="">UPDATE</a> / <a href="delete.do?id=<%=customer.getId()%>" class="delete">DELETE</a></td>
    </tr>

    <%
        }
    %>

    <%
        }
    %>
</table>
</body>
</html>
