<%@ page import="xbt.web.bean.Dept" %><%--
  Created by IntelliJ IDEA.
  User: xbt
  Date: 2023/6/20
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>edit</title>
</head>
<body>
    <!--submit要和form一起使用!-->
    <form action="<%=request.getContextPath()%>/Dept/edit" method="post">
        <% Dept dept=(Dept) request.getAttribute("dept"); %>
        部门编号：<input type="text" name="deptno" value="<%=dept.getDeptno()%>" readonly><br>
        部门名称：<input type="text" name="dname" value="<%=dept.getDname()%>"><br>
        部门位置：<input type="text" name="loc" value="<%=dept.getLoc()%>"><br>
        部门详情：<input type="text" name="detail" value="<%=dept.getDetail()%>"><br>
        <input type="submit",value="提交">
    </form>
</body>
</html>
