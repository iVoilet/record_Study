<%--
  Created by IntelliJ IDEA.
  User: xbt
  Date: 2023/6/20
  Time: 22:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>欢迎&登陆页面</title>
  </head>
  <body>
  <h1>欢迎！请登录</h1>
  <hr>
  <!--网页上是需要加根路径的-->
  <form action="<%=request.getContextPath()%>/user/login" method="post">
    账号：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    十天免登录<input type="checkbox" name="f" value="1"><br>
    <input type="submit" value="登录">
  </form>
  </body>
</html>
