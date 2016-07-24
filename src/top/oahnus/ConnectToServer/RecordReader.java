package top.oahnus.ConnectToServer;

import top.oahnus.Bean.Message;
import top.oahnus.Main.MemberPanel;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by oahnus on 2016/7/22.
 */
public class RecordReader implements Runnable{
    public static final String SERVERIP = "127.0.0.1";
    private Socket socket;
    private File file;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private List<MemberPanel> memberPanelList;
    private String clientID;

    public RecordReader(List<MemberPanel> memberPanelList,String clientID){
        this.memberPanelList = memberPanelList;
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try{
System.out.println("查询是否有离线链接");
            socket = new Socket(SERVERIP,8885);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            Message login = new Message();
            login.setCode("LOGIN");
            login.setSourceID(clientID);

            oos.writeObject(login);
            oos.flush();

System.out.println("查询消息发出");
            Message message = (Message) ois.readObject();
System.out.println("接受到结果");
            while(!message.getCode().equals("END")){
                //循环读取全部消息
                //将信息提取出具体内容，添加到相对应ID的memberPnel中
                String sourceID = message.getSourceID();
                String content = message.getContent();

                for(int i=0;i<memberPanelList.size();i++){
                    MemberPanel memberPanel = memberPanelList.get(i);
                    if(memberPanel.getFriendID().equals(sourceID)){
                        memberPanel.setHasNewMsg("新消息");
                        if(!(content.equals("")||content==null)) {
System.out.println(content);
                            memberPanel.getMessages().add(content);
                        }
                    }
                }
                message = (Message) ois.readObject();
            }

            //结束后关闭这个线程
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
