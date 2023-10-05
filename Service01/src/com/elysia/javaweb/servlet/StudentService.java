package com.elysia.javaweb.servlet;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class StudentService implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //设置响应的内容
        servletResponse.setContentType("text/html");
        PrintWriter out= servletResponse.getWriter();
        //连接数据库
        Connection coon=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            String url="jdbc:mysql://localhost:3306/xg210311xbt";
            String user="root";
            String password="@Xbt2863749";
            coon= DriverManager.getConnection(url,user,password);
            //获取预编译的数据库操作对象
            String sql="select name from compy";
            ps=coon.prepareStatement(sql);
            //执行SQL
            rs=ps.executeQuery();
            //处理结果集
            while(rs.next()){
                String name=rs.getString("name");
                System.out.println("成员："+name);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally{
            //释放资源
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ps!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(coon!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }//根据原有JavaSE是没有这个接口的，所以要进行导包

}
