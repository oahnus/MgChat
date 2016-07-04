package top.oahnus.Dao;

/**
 * Created by oahnus on 2016/6/17.
 */

import top.oahnus.Bean.User;
import top.oahnus.Util.JDBCUtil;
import top.oahnus.Util.MD5Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 登录验证DAO
 */
public class LoginAuthDAO {
    Connection conn = null;
    PreparedStatement stat = null;
    ResultSet rs = null;

    User user = null;

    public User verifyUser(String userid, String pass){
        boolean success = false;
        conn = JDBCUtil.getConnection();

        //将明文pass加密成md5字符串
        String password = MD5Util.encode2hex(pass);

        //拼接查询语句
        String sql = "SELECT user_id, username, password, info, born, " +
                "sex, address, figure FROM user WHERE " +
                "user_id='"+userid+"'&password='"+password+"'";

        //查询数据
        try {
            stat = conn.prepareStatement(sql);
            rs = stat.executeQuery();

            if(rs.next()){
                success = true;
//System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+
//        " "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
                user = new User();

                user.setUserID(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setInfo(rs.getString(4));
                user.setBornDate(rs.getDate(5));
                user.setSex(rs.getString(6));
                user.setAdress(rs.getString(7));
                user.setFigure(rs.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.close(conn,stat,rs);
        }
        return user;
    }
}
