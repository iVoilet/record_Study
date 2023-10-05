<%@ page import="xbt.web.util.C_JDBC" %>
<%@ page import="xbt.web.bean.Dept" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: xbt
  Date: 2023/6/20
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list</title>
</head>
<body>
<h1>你好</h1>
<table border="1px" width="50%" height="100px" align="center">

    <script type="text/javascript">
        function del(dno){
            var ok = window.confirm("亲，删了不可恢复哦！");
            if(ok){
                document.location.href = "<%=request.getContextPath()%>/Dept/delete?deptno=" + dno;
            }
        }
    </script>


    <tr>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>部门位置</th>
        <th>操作</th>
    </tr>
    <!--要实现动态的链接，所以得连接数据库-->
<%--    <tr>--%>
<%--        <th>10</th>--%>
<%--        <th>研发部</th>--%>
<%--        <th>张三</th>--%>
<%--        <th>--%>
<%--
<%--            <a href="edit.jsp">修改</a>--%>
<%--            <a href="detail.jsp">详情</a>--%>
<%--        </th>--%>
<%--    </tr>--%>
    <!--先使用JSP的Java代码。后面再使用EL表达式-->
    <%
        //这个getAttribute()括号里面要的是名字吗？对的，就是在前面写的名字，通过映射list可以得到需要对象
        //这个强转够我学两年半了
        List<Dept> list =(List<Dept>) request.getAttribute("list");
        for (Dept dept:list)
        {
            //这里需要写HTML的语言，所以需要分开！
    %>
    <tr>
        <th><%=dept.getDeptno()%></th>
        <th><%=dept.getDname()%></th>
        <th><%=dept.getLoc()%></th>
        <th>
            <!--这里的”？“后面的内容我有点没懂-->
            <a href="<%=request.getContextPath()%>/Dept/detail?f=edit&deptno=<%=dept.getDeptno()%>">修改</a>
            <a href="<%=request.getContextPath()%>/Dept/detail?f=detail&deptno=<%=dept.getDeptno()%>">详情</a>
            <a href="javascript:void(0)" onclick="del(<%=dept.getDeptno()%>)">删除</a>
        </th>
    </tr>
    <%
        }
    %>
    <br>
    <hr>
    <a href="<%=request.getContextPath()%>/add.jsp">添加部门</a>

</table>
</body>
</html>
