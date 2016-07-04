package top.oahnus.LoginIn;

import top.oahnus.Bean.User;
import top.oahnus.Dao.LoginAuthDAO;

/**
 * Created by oahnus on 2016/6/17.
 */
public class LoginAuth {

    /**
     * 登录验证 Login authentication
     * @param username
     * @param password
     * @return success return true,other return false
     */
    public User verify(String username, String password){
        LoginAuthDAO loginAuthDAO = new LoginAuthDAO();

        return loginAuthDAO.verifyUser(username,password);
    }


    public static void main(String[] args){
        LoginAuth loginAuth = new LoginAuth();
        System.out.println(loginAuth.verify("10000","12345"));
    }
}
