<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/21
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>确认收货地址和信用卡信息</title>
</head>
<body>
<h4>第二步：请输入收货地址和信用卡信息：</h4>
<form action="<%= request.getContextPath() %>/processStep2", method="post">
    <table cellpadding="10" cellspacing="0" border="1">
        <tr>
            <td colspan="2">寄送信息</td>
        </tr>
        <tr>
            <td>姓名：</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>地址：</td>
            <td><input type="text" name="address"></td>
        </tr>
        <tr>
            <td colspan="2">信用卡信息</td>
        </tr>
        <tr>
            <td>种类：</td>
            <td>
                <input type="radio" name="cardType" value="Visa"/>Visa
                <input type="radio" name="cardType" value="Master"/>Master
            </td>
        </tr>
        <tr>
            <td>卡号：</td>
            <td><input type="text" name="card"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form>
</body>
</html>
