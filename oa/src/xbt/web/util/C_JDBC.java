package xbt.web.util;

import java.sql.*;
import java.util.ResourceBundle;

public class C_JDBC {
    //实现JDBC的任务，减少重写次数

    public static ResourceBundle resourceBundle=ResourceBundle.getBundle("resource.jdbc");
    public static String driver= resourceBundle.getString("driver");
    public static String url=resourceBundle.getString("url");
    public static String root=resourceBundle.getString("use");
    public static String password=resourceBundle.getString("password");

    //用静态代码块来实现注册驱动
    //因为驱动注册只需要一次
    static{
        try {
            //forName要的是一个“完整类名”，从包名开始的那种。而driver报名里面的
            //就是驱动的完整包名
            //这一步就可以获得Driver对象
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //封装方法来获得Connection对象
    //写成静态的，就可以直接类名调用，方便了不少了
    public static Connection getConnection() throws SQLException {
        Connection conn=DriverManager.getConnection(url,root,password);
        return conn;
    }

    //实现关闭的方法
    //为什么这里是Statement而不是PrepareStatement
    public static void close(Connection conn,Statement ps,ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static void main(String[] args) {
//        Connection conn=null;
//        PreparedStatement ps=null;
//        ResultSet rs=null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn=DriverManager.getConnection();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
