package top.oahnus.Main;

import javafx.scene.text.*;
import top.oahnus.Bean.User;
import top.oahnus.ConnectToServer.RecordReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by oahnus on 2016/6/17.
 */
public class MainFrame extends JFrame{

    private User user       = null;
    //关闭，最小化按钮
    private JLabel close    = new JLabel();
    private JLabel min      = new JLabel();
    //背景
    private JLabel back     = new JLabel();
    //昵称，个人简介
    private JLabel username = new JLabel();
    private JLabel userInfo = new JLabel();
    //好友列表滚动条
    private JScrollPane scrollPane;
    //好友列表
    private FriendsPanel friendsPanel;
    //个人信息
    private JPanel userInfoPanel = new JPanel();
    //头像
    private JButton userFigure   = new JButton();
    //系统托盘
    SystemTray systemTray        = SystemTray.getSystemTray();

    private MainFrame self       = this;
    //记录鼠标在窗体内的坐标，留待实现窗体拖动使用
    private int mouseX           = 0;
    private int mouseY           = 0;
    //设定鼠标在某一范围内点击拖动有效,true 有效，false 无效
    private boolean isCanMoved   = true;

    public static final int WINDOWWIDTH  = 280;
    public static final int WINDOWHEIGHT = 640;

    Image minIcon,closeIcon,userIcon,background,icon;

    public MainFrame(User user){
        this.user     = user;
        friendsPanel  = new FriendsPanel(user);
        scrollPane    = new JScrollPane(friendsPanel);
    }

    public void launch(){
        //判断是否有离线信
        RecordReader recordReader = new RecordReader(friendsPanel.getFriendList(),user.getUserID());
        Thread thread = new Thread(recordReader);
        thread.start();

        setting();
        addListener();
    }

    public void setting() {
        //设置无边框
        setUndecorated(true);
        //取消默认布局管理
        setLayout(null);
        //禁止改变大小
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //加载图标图片
        try {
            minIcon    = ImageIO.read(new File("resource/sub.png"));
            closeIcon  = ImageIO.read(new File("resource/close.png"));
            background = ImageIO.read(new File("resource/background.jpg"));
            icon       = ImageIO.read(new File("resource/icon.jpg"));
            userIcon   = ImageIO.read(new File(user.getFigure()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置窗体尺寸，位置
        setBounds(1000, 10, WINDOWWIDTH,WINDOWHEIGHT);

        //控件尺寸位置
        close.setBounds(WINDOWWIDTH-20,0,20,20);
        min.setBounds(WINDOWWIDTH-40,0,20,20);
        back.setBounds(0,0,WINDOWWIDTH,160);

        //用户信息panel设置
        userInfoPanel.setBounds(0,30,WINDOWWIDTH,140);
        userInfoPanel.setLayout(null);

        //用户信息面板设置
        //添加html标签，设置字体颜色
        username.setText("<html><p color='white'>"+user.getUsername()+"</P></html>");
        userInfo.setText("<html><p color='white'>"+user.getInfo()+"</p></html>");

        userFigure.setBounds(10,10,80,80);
        username.setBounds(110,40,80,40);
        username.setFont(new Font("Microsoft YaHei", Font.PLAIN,24));
        userInfo.setBounds(110,80,200,40);
        userInfo.setFont(new Font("Microsoft YaHei", Font.PLAIN,14));

        //设置图标
        setIconImage(icon);
//        Image closeImage = closeIcon.getScaledInstance(20,20,Image.SCALE_DEFAULT);
        close.setIcon(new ImageIcon(closeIcon));
//        Image minImage = minIcon.getScaledInstance(20,20,Image.SCALE_DEFAULT);
        min.setIcon(new ImageIcon(minIcon));
        Image backImage = background.getScaledInstance(back.getWidth(),back.getHeight(),Image.SCALE_DEFAULT);
        back.setIcon(new ImageIcon(backImage));

        userFigure.setIcon(new ImageIcon(userIcon));

        //滚动条设置
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0,180,WINDOWWIDTH,WINDOWHEIGHT-200);
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new Dimension(WINDOWWIDTH,WINDOWHEIGHT-200));

        //添加控件到面板
        getContentPane().add(close);
        getContentPane().add(min);
        getContentPane().add(back);
        getContentPane().add(scrollPane);

        //用户信息面板
        userInfoPanel.add(userFigure);
        userInfoPanel.add(username);
        userInfoPanel.add(userInfo);
        getContentPane().add(userInfoPanel);
        //将用户名置于back之上
        this.getContentPane().setComponentZOrder(username,1);
        this.getContentPane().setComponentZOrder(userInfo,1);

        //显示主面板
        setVisible(true);
    }

    private void addListener(){
        //添加无边框窗体的拖动功能
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                if(mouseY>back.getHeight()){
                    isCanMoved = false;
                }else{
                    isCanMoved = true;
                }
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isCanMoved){
                    //获取鼠标在屏幕中的坐标
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();

                    if(y<0) {
                        y = 0;
                    }
                    int positionX = x-mouseX;
                    int positionY = y-mouseY;
                    //窗体置顶
                    if(positionY<0){
                        positionY=0;
                    }

                    //设置窗体位置
                    self.setLocation(positionX,positionY);
                }
            }
        });
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        min.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO
                addSystemTray();
            }
        });
    }

    public void addSystemTray(){
        TrayIcon trayIcon = null;

        if(SystemTray.isSupported()){
            //创建系统托盘

            trayIcon = new TrayIcon(icon);
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                }
            });
            try {
                if(systemTray.getTrayIcons().length==0) {
                    systemTray.add(trayIcon);
                }
            } catch (AWTException e) {
                e.printStackTrace();
            }
            setVisible(false);
        }
    }
}
