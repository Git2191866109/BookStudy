<%@ page import="com.litian.mvc.domain.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/4/28
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改用户</title>
</head>
<body>
<%----<%=request.getParameter("name")%>----%>
<%
    Object msg = request.getAttribute("message");
    if (msg != null) {
%>
<br>
<font color="red"><%=msg%>
</font>
<br>
<%
    }
    String id = null;
    String oldName = null;
    String name = null;
    String address = null;
    String phone = null;

    Customer cc = (Customer) request.getAttribute("customer");

    // 如果cc不为空，则是从点击修改连接过来的
    // 若为空，则是出错之后返回而来的（名字已被占用）
    if (cc != null) {
        id = cc.getId() + "";
        oldName = cc.getName();
        name = cc.getName();
        address = cc.getAddress();
        phone = cc.getPhone();
    }else{
        id = request.getParameter("id");
        oldName = request.getParameter("oldName");
        name = request.getParameter("oldName");
        address = request.getParameter("address");
        phone = request.getParameter("phone");
    }
%>

<form action="update.do" method="post">

    <%--  隐藏域  --%>
    <input type="hidden" name="id" value="<%=id%>"/>
    <input type="hidden" name="oldName" value="<%=oldName%>"/>

    <table>
        <tr>
            <td>CustomerName:</td>
            <td><input type="text" name="name"
                       value="<%=name%>"/></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address"
                       value="<%=address%>"/></td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td><input type="text" name="phone"
                       value="<%=phone%>"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="提交"></td>
        </tr>
    </table>
</form>
</body>
</html>
