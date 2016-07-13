package top.oahnus.ConnectToServer;

import top.oahnus.Bean.User;
import top.oahnus.Main.MemberPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
public class FriendsStateMonitor implements Runnable{
    private Socket socket                = null;
    private DataInputStream dis          = null;
    private DataOutputStream dos         = null;
    private boolean isConnected          = false;

    private List<User> friends           = null;
    private Map<String,String> friendsIP = new HashMap<>();

    private String ipAddress             = null;

    public FriendsStateMonitor(User user){
        this.friends = user.getFriendsList();
    }

    /**
     * 初始化，创建痛服务器的连接
     */
    public void connectToServer(){
        try {
            socket      = new Socket("127.0.0.1",8889);
            dos         = new DataOutputStream(socket.getOutputStream());
            dis         = new DataInputStream(socket.getInputStream());
            isConnected = true;
            ipAddress   = InetAddress.getLocalHost().getHostAddress();

            dos.writeUTF(ipAddress);
            dos.flush();
        }catch(BindException e){
System.out.println("8889端口被占用");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param friendsID 好友账号ID
     * @return 如果好友在线，返回ip地址；如果离线，返回null
     */
    private String getIpAddress(String friendsID){
        String ipAddress = null;
        try {
            dos.writeUTF(friendsID);
            dos.flush();

            ipAddress = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            return ipAddress;
        }
    }

    public void close(){
        try {
            if(dis    != null){dis.close();}
            if(dos    != null){dos.close();}
            if(socket != null){socket.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String stateString = "";

        //查询所有好友的ip
        for(int i=0;i<friends.size();i++){
            String ID = friends.get(i).getUserID();
            String ip = getIpAddress(ID);
            if(ip != null){
                friendsIP.put(ID, ip);
            }
        }

        while(isConnected){
            try {
                //监听是否有好友有新的状态转变
                //返回格式为 "ID#IP"
                stateString = dis.readUTF();

                String id = stateString.split("#")[0];
                String ip = stateString.split("#")[1];

                if(friendsIP.containsKey(id)){
                    friendsIP.put(id, ip);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
