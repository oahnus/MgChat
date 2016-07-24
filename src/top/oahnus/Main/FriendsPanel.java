package top.oahnus.Main;

import top.oahnus.Bean.User;
import top.oahnus.ConnectToServer.FriendsStateMonitor;
import top.oahnus.ConnectToServer.RecordReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.List;

/**
 * Created by oahnus on 2016/6/30.
 */
public class FriendsPanel extends JPanel {

    public static final int FIGUREICONWIDTH = 20;
    private User user                       = null;
    private Image image                     = null;
    //默认头像
    private Image defaultImage              = null;
    private List<MemberPanel> friendList    = new ArrayList<>();
    //好友下拉列表
    private JLabel panelTitle               = null;
    //记录下拉列表点击次数，判断下拉收起
    private int clickNum                    = 0;
    //图标资源
    private ImageIcon tabUpIcon,tabDownIcon;


    public FriendsPanel(User user){
        super();
        this.user = user;
        initialize();
    }

    private void initialize() {
        panelTitle = new JLabel();
        try {
            getImageFromResource();
        } catch (IOException e) {
//            e.printStackTrace();
System.out.println("无法获取图片资源");
        }

        setting();
        addListener();
    }

    private void setting(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setSize(280,400);
        this.setLocation(20,200);

        panelTitle.setIcon(tabDownIcon);
        panelTitle.setText("我的好友");

        this.add(panelTitle);

        //加载默认头像
        try {
            defaultImage = ImageIO.read(new File("resource/icon.jpg"));
            defaultImage = defaultImage.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将从server获取的好友添加到列表中

        List<User> list = user.getFriendsList();
        //获取好友列表中好友的详细信息，并设置在MemberPanel

        for(int i=0;i<list.size();i++){
            User friend = list.get(i);
//            MemberPanel memberPanel = new MemberPanel(friend.getUsername(),friend.getInfo(),defaultImage);
            MemberPanel memberPanel = new MemberPanel(friend,user,defaultImage);
            friendList.add(memberPanel);
        }

        for(MemberPanel panel:friendList){
            panel.setPreferredSize(new Dimension(this.getWidth()-5,50));
//            panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            this.add(panel);
            panel.setVisible(false);
        }
//
//System.out.println("读取离线");
//        RecordReader recordReader = new RecordReader();
//System.out.println("123123123");
//        recordReader.connectToServer();
//        File file = recordReader.getFile();
//
//System.out.println("File is null?"+file == null);
//System.out.println("读取完成");
        panelTitle.setIcon(tabUpIcon);
        for(MemberPanel panel:friendList){
            panel.setVisible(true);
        }
        update();

        this.setVisible(true);

        for(MemberPanel panel:friendList){
            panel.setHasNewMsg("new Message");
        }
    }

    private void addListener(){
        panelTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickNum++;

                if(clickNum%2==0){
                    panelTitle.setIcon(tabDownIcon);
                    for(MemberPanel panel:friendList){
                        panel.setVisible(false);
                    }
                    update();
                }else{
                    panelTitle.setIcon(tabUpIcon);
                    for(MemberPanel panel:friendList){
                        panel.setVisible(true);
                    }
                    update();
                }
            }
        });

        //添加好友在线状态的监视类
//        FriendsStateMonitor monitor = new FriendsStateMonitor(user);
//        monitor.connectToServer();
//
//        Thread monitorThread = new Thread(monitor);
//        monitorThread.start();
    }

    private void getImageFromResource() throws IOException {
        Image tabUp,tabDown;

        tabUp = ImageIO.read(new File("resource/tab_up.png"));
        tabUp = tabUp.getScaledInstance(FIGUREICONWIDTH,FIGUREICONWIDTH,Image.SCALE_DEFAULT);
        tabDown = ImageIO.read(new File("resource/tab_down.png"));
        tabDown = tabDown.getScaledInstance(FIGUREICONWIDTH,FIGUREICONWIDTH,Image.SCALE_DEFAULT);

        tabUpIcon = new ImageIcon(tabUp);
        tabDownIcon = new ImageIcon(tabDown);
    }

    public void update(){
        updateUI();
    }

    public List<MemberPanel> getFriendList() {
        return friendList;
    }
}
