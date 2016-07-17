package top.oahnus.ConnectToServer;

import top.oahnus.Bean.User;
import top.oahnus.Main.MemberPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
 * 连接服务器，通过发送好友ID来通过服务端获取该好友的ip地址
 */
public class FriendsStateMonitor{
    private Socket socket                = null;
    private DataOutputStream dos         = null;
    private DataInputStream dis        = null;

    private List<User> friends           = null;
//    public static Map<String,String> friendsIP = new HashMap<>();

    private String ipAddress             = null;
    private User user;

    public FriendsStateMonitor(User user){
        this.user = user;
    }

    /**
     * 初始化，创建痛服务器的连接
     */
    public String connectToServer(){
        try {
            socket      = new Socket("127.0.0.1",8889);
            dos         = new DataOutputStream(socket.getOutputStream());
            dis         = new DataInputStream(socket.getInputStream());
            ipAddress   = InetAddress.getLocalHost().getHostAddress();

            String msg = "GETIP#"+user.getUserID();

            dos.writeUTF(msg);
            dos.flush();

System.out.println(msg);

            ipAddress = dis.readUTF();

        }catch(BindException e){
System.out.println("8889端口被占用");
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
//            close();
        }

        return ipAddress;
    }

    private void close(){
        try {
            if(dis    != null){dis.close();}
            if(dos    != null){dos.close();}
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

    public static void main(String[] args){
        User user = new User();
        user.setUserID("10001");

        FriendsStateMonitor monitor = new FriendsStateMonitor(user);
        String s = monitor.connectToServer();
        System.out.println(s);

        FriendsStateMonitor monitor1 = new FriendsStateMonitor(user);
        String ss = monitor1.connectToServer();
        System.out.println(ss);
//        monitor.test();
    }
}
