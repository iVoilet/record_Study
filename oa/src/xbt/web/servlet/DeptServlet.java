package xbt.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import xbt.web.bean.Dept;
import xbt.web.util.C_JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//写注解，注解里面的内容是指，当指向的是直接里面的路径的时候，就会执行这里的方法
//然后这个方法是总方法，来处理前端的各个按钮的请求
@WebServlet({"/Dept/list","/Dept/add","/Dept/delete","/Dept/detail","/Dept/edit"})
public class DeptServlet extends HttpServlet {
    //重写的是service方法（哭）
    //重写这个方法来面对post.get等等
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        //仍然是区分各种path的值
        String getPath=request.getServletPath();
        if(getPath.equals("/Dept/list")){
            doList(request,response);
        }else if(getPath.equals("/Dept/add")){
            doAdd(request,response);
        }else if(getPath.equals("/Dept/delete")){
            doDelete(request,response);
        }else if(getPath.equals("/Dept/detail")){
            doDetail(request,response);
        }else if(getPath.equals("/Dept/edit")){
            doEdit(request,response);
        }

    }


    public void doList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //既然是跳转到list页面的话，那我们的主要任务就是实现从数据库把数据打到前端
        //考虑使用request的域
        //然后用Java的pojo类（bean）的List来实现存储，我认为这个很牛的思想
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        //这个拿来存放Dept类集合
        List<Dept> list=new ArrayList<>();

        try {
            conn= C_JDBC.getConnection();
            String sql="select deptno,dname,loc from dept";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                //这里又涉及了一个知识点就是关于如何从rs集合中获取数据
                //使用的还是映射
                Dept dept=new Dept(rs.getInt("deptno"),rs.getString("dname"),rs.getString("loc"));
                //然后把dept放到list里面去
                list.add(dept);
            }
            //执行完了也就意味着取完了数据，我们要做的是把这个数据从当前传到list.jsp
            //具体展示则由JSP实现
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //把这个集合放到请求域中
        request.setAttribute("list",list);

        //下一步要做的事把这请求域传过去。
        //用重定向还是转发？转发，不然不再一起请求域中
        //第一个是指你要传到那里
        //第二指的是要穿什么
        //这里有个异常要处理
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }



    public void doAdd(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //这里我们需要考虑的内容就是如何获得从前端传来的4个数据，至于加入其实是非常简单的
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc = request.getParameter("loc");
        String detail=request.getParameter("detail");

        //然后要连接数据库，把这个内容传给数据库
        Connection conn=null;
        PreparedStatement ps=null;
        int count=0;
        try {
            conn=C_JDBC.getConnection();
            String sql="insert into dept(deptno,dname,loc,detail) values(?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,deptno);
            ps.setString(2,dname);
            ps.setString(3,loc);
            ps.setString(4,detail);
            count=ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            C_JDBC.close(conn,ps,null);
        }

        //当我们操作结束之后，是需要回去的，来展示一下
        //用个重定向把？
        if (count==1){
            //错误的，不可以直接去list.jsp
            //得先从DeptServlet过去，不然穿不了List
            //遇见了一个很怪的现象，我刚刚这里写的小写的，虽然会报错，但是会正常的加上去，好怪
            //不怪不怪，因为这里就是执行了加了，只是没有实现事务而已..
            response.sendRedirect(request.getContextPath() + "/Dept/list");
        }
    }

    public void doDelete(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //做删除、详情、修改操作其实和前面的内容是不一样的，它们相对而言多了一个操作
        //这个操作是定位到这一行，而这个操作可以通过JSP里面传的值来
        Dept dept=new Dept();
        String deptno=request.getParameter("deptno");

        //然后要连接数据库，把这个内容传给数据库
        Connection conn=null;
        PreparedStatement ps=null;
        int count=0;
        try {
            conn=C_JDBC.getConnection();
            String sql="delete from dept where deptno=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,deptno);
            count=ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            C_JDBC.close(conn,ps,null);
        }

        //当我们操作结束之后，是需要回去的，来展示一下
        //用个重定向把？
        if (count==1){
            //删除了之后要回去，类似于之前的内容
            response.sendRedirect(request.getContextPath() + "/Dept/list");
        }
    }

    public void doDetail(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        //做删除、详情、修改操作其实和前面的内容是不一样的，它们相对而言多了一个操作
        //这个操作是定位到这一行，而这个操作可以通过JSP里面传的值来
        Dept dept=new Dept();
        String deptno=request.getParameter("deptno");

        //然后要连接数据库，把这个内容传给数据库
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn=C_JDBC.getConnection();
            String sql="select deptno,dname,loc,detail from dept where deptno=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,deptno);
            rs=ps.executeQuery();
            if(rs.next()){
                //我觉得还是有必要去了解一下ResultSet底层的数据结构的
                dept.setDeptno(Integer.parseInt(deptno));
                dept.setDname(rs.getString("dname"));
                dept.setLoc(rs.getString("loc"));
                dept.setDetail(rs.getString("detail"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            C_JDBC.close(conn,ps,rs);
        }

        //当查询成功了，我们将要去detail.jsp
        //感觉用个转发就ok了
        //哎呀，我的思维被前俩局限了。其实这里只需要用个咖啡豆装一下，然后穿个咖啡豆过去就好了
        //注意，一般要用转发的时候，都是在外面要用到请求域携带内容的时候
        request.setAttribute("dept",dept);
        request.getRequestDispatcher("/" + request.getParameter("f") + ".jsp").forward(request, response);

    }

    public void doEdit(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        //做删除、详情、修改操作其实和前面的内容是不一样的，它们相对而言多了一个操作
        //这个操作是定位到这一行，而这个操作可以通过JSP里面传的值来
        //就是以Paremeter形式传过来的
        String deptno=request.getParameter("deptno");
        String dname=request.getParameter("dname");
        String loc=request.getParameter("loc");
        String detail=request.getParameter("detail");

        //然后要连接数据库，把这个内容传给数据库
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn=C_JDBC.getConnection();
            String sql="update dept set dname=?,loc=?,detail=? where deptno=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            ps.setString(3,detail);
            ps.setString(4,deptno);

            //关键！这个玩意不能少，虽然返回值没啥用
            //没有下面这句语句就不会执行上面的内容！
            int count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            C_JDBC.close(conn,ps,null);
        }

        //修改完之后应该选择的是回到list页面
        //而就如我们所想的那样，我们要回去需要从DeptServlet的/Dept/list过，不然无法显示
        //重定向即可
        //重定向需要根路径
        response.sendRedirect(request.getContextPath()+"/Dept/list");


    }

}
