package top.oahnus.ConnectToServer;

import top.oahnus.Bean.Message;
import top.oahnus.Bean.User;
import top.oahnus.Main.MemberPanel;

import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oahnus on 2016/7/13.
 */

/**
 * 暂时舍弃，留待后用
 */
public class FriendsStateMonitor{
    private Socket socket                = null;
    private ObjectOutputStream oos         = null;
    private ObjectInputStream ois        = null;

    private List<User> friends           = null;
//    public static Map<String,String> friendsIP = new HashMap<>();

    private String ipAddress             = null;
    private User user;
    private Message msg = new Message();

    public FriendsStateMonitor(User user){
        this.user = user;
    }

    /**
     * 初始化，创建痛服务器的连接
     */
    public void connectToServer(){
        try {
            socket      = new Socket("127.0.0.1",8888);
            oos         = new ObjectOutputStream(socket.getOutputStream());
            ois         = new ObjectInputStream(socket.getInputStream());
            ipAddress   = InetAddress.getLocalHost().getHostAddress();

            msg.setCode("LOGIN");
            msg.setContent(user.getUserID());

            oos.writeObject(msg);
            oos.flush();
System.out.println("LOGIN");
        }catch(BindException e){
System.out.println("8888端口被占用");
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
//            close();
        }
    }

    private void close(){
        try {
            if(ois    != null){ois.close();}
            if(oos    != null){oos.close();}
            if(socket != null){socket.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void test(){
//        try {
//            Map<String,String> ips = (Map<String, String>) ois.readObject();
//            System.out.println(ips.get("10001"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
