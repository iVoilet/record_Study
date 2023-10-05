package xbt.web.servlet;

//本servlet是用来实现登录与登出的功能的

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import xbt.web.bean.User;
import xbt.web.util.C_JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login","/user/logout"})
public class userServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path=request.getServletPath();
        if("/user/login".equals(path)){
            doLogin(request,response);
        }
    }

    public void doLogin(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        //设置这个参数的意义是实现判断后面账号密码能不能配对上
        boolean flag=false;

        //首先应该做的事获得从前端传来的那俩参数
        //用的方法是request.getParameter
        String username=request.getParameter("username");
        String password = request.getParameter("password");

        //接下去要做的就是对比这里的数据和数据库里面的数据，
        //如果能匹配上的话，那么就可以登录，否则无法登录上去
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn=C_JDBC.getConnection();
            String sql="select username from user where username=? and password=?";
            ps=conn.prepareStatement(sql);
            //千万不要忘了给“？”赋值
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            //rs是接收集合，它指向的是第一条数据之前
            //而这里只可能会有一条数据
            if(rs.next()){
                flag=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            C_JDBC.close(conn,ps,rs);
        }
        //关闭流
        //C_JDBC.close(conn,ps,rs);直接放在finally上面关

        if(flag){
            //这个最大的作用是设置一个Session
            //为啥是请求一个session而不是自己new一个session呢？
            HttpSession session=request.getSession();
            User user=new User(username,password);
            session.setAttribute("user",user);

            String f=request.getParameter("f");
            if(f!=null) {
                if (f.equals("1")) {
                    //如果这里成立，那么就放到cookie里面去

                    Cookie cookie1 = new Cookie("username", username);
                    Cookie cookie2 = new Cookie("password", password);

                    //要设置的10天
                    cookie1.setMaxAge(60 * 60 * 24 * 10);
                    cookie2.setMaxAge(60 * 60 * 24 * 10);

                    // 设置cookie的path（只要访问这个应用，浏览器就一定要携带这两个cookie）
                    cookie1.setPath(request.getContextPath());
                    cookie2.setPath(request.getContextPath());
                    // 响应cookie给浏览器
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }
            }
            //这里进行一个重定向
            //response.sendRedirect(request.getContextPath()+"/list.jsp");
            //不对，不应该直接去list.jsp，这样子搞的话会导致jsp里面写连接数据库的代码
            //无法实现更好的分离，所以应该写在DeptServlet，从这里跳转到DeptServlet
            response.sendRedirect(request.getContextPath()+"/Dept/list");
        }else{
            response.sendRedirect(request.getContextPath()+"/loginError.jsp");
        }
    }
}
