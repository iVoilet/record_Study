<%--
  Created by IntelliJ IDEA.
  User: xbt
  Date: 2023/6/23
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add</title>
</head>
<body>
<!--不用犹豫什么，JSP自带了9大内置类，这里可以直接使用request-->
<form action="<%=request.getContextPath()%>/Dept/add">
    部门编号：<input type="text" name="deptno"><br>
    部门名称：<input type="text" name="dname"><br>
    部门地址：<input type="text" name="loc"><br>
    部门详情：<input type="text" name="detail"><br>
    <!--submit这里不需要加名字-->
    <input type="submit" value="提交">
</form>
</body>
</html>
