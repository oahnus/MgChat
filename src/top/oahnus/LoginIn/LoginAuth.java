package top.oahnus.LoginIn;

import top.oahnus.Bean.User;
import top.oahnus.Extra.Alert;
import top.oahnus.Util.MD5Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by oahnus on 2016/6/17.
 */

// 通过Socket与服务端链接，将输入的用户信息封装到User中，发送到服务端验证，
// 若成功，返回从数据库中获取的User信息
public class LoginAuth {

    //链接服务端
    private Socket socket = null;
    //Object流
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * 登录验证 Login authentication
     * @param userID 用户账号
     * @param password 用户密码
     * @return success return true,other return false
     */
    public User verify(String userID, String password) {
//        LoginAuthDAO loginAuthDAO = new LoginAuthDAO();
        //本地用户信息
        User localUserInfo = new User();
        //远程用户信息
        User remoteUserInfo = null;

        password = MD5Util.encode2hex(password);

        localUserInfo.setUserID(userID);
        localUserInfo.setPassword(password);

//System.out.println(password);

        try {
            socket = new Socket("127.0.0.1",8887);

            out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(localUserInfo);
            out.flush();

System.out.println("read");

            socket.shutdownOutput();

//Thread.sleep(100);

            in = new ObjectInputStream(socket.getInputStream());
//System.out.println(in == null);
            remoteUserInfo = (User) in.readObject();

//System.out.println(remoteUserInfo == null);
//System.out.println(remoteUserInfo.getUsername());
//System.out.println(remoteUserInfo.getBornDate());
        } catch (IOException e) {
            Alert alert = new Alert("网络异常!无法连接到服务器");
            alert.showAlert();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return loginAuthDAO.verifyUser(username,password);
        return remoteUserInfo;
    }

    public static void main(String[] args){
        LoginAuth loginAuth = new LoginAuth();
        System.out.println(loginAuth.verify("10000","12345"));
    }
}
