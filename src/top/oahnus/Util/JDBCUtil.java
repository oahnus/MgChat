package top.oahnus.Util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by oahnus on 2016/6/16.
 */
public class JDBCUtil {

    private static Properties properties = new Properties();

    static{
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("top/oahnus/Config/DB.properties");

        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(properties.getProperty("driverClassName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String pass = properties.getProperty("password");

        try {
            return DriverManager.getConnection(url,user,pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn, PreparedStatement stat, ResultSet rs){
        try {
            if(rs!=null) {
                rs.close();
            }
            if(stat!=null){
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            rs=null;
            stat = null;
            conn = null;
        }
    }
}
