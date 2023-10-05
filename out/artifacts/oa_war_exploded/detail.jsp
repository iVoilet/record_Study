<%@ page import="xbt.web.bean.Dept" %><%--
  Created by IntelliJ IDEA.
  User: xbt
  Date: 2023/6/20
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>detali</title>
</head>
<body>
<%  Dept detp=(Dept)request.getAttribute("dept");%>
    部门编号：<%=detp.getDeptno()%>
    <br>
    部门名称：<%=detp.getDname()%>
    <br>
    地址：<%=detp.getLoc()%>
    <br>
    详情：<%=detp.getDetail()%>
    <br>
    <input type="button" value="返回" onclick="window.history.back()">
</body>
</html>
