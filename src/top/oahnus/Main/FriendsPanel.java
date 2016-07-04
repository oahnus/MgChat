package top.oahnus.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by oahnus on 2016/6/30.
 */
public class FriendsPanel extends JPanel {

    public static final int FIGUREICONWIDTH = 20;

    private List<MemberPanel> friendList = new ArrayList<>();

    //好友下拉列表
    private JLabel panelTitle;

    //记录下拉列表点击次数，判断下拉收起
    private int clickNum = 0;

    //图标资源
    private ImageIcon tabUpIcon,tabDownIcon;
    public FriendsPanel(){
        super();
        initialize();
    }

    private void initialize() {
        panelTitle = new JLabel();
        try {
            getImageFromResource();
        } catch (IOException e) {
//            e.printStackTrace();
            //TODO
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
//        test();
        Image image = null;
        try {
            image = ImageIO.read(new File("resource/icon.jpg"));
            image = image.getScaledInstance(40,40,Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MemberPanel memberPanel = new MemberPanel("jack","huuu",image);
        MemberPanel memberPanel2 = new MemberPanel("tom","huuu",image);
        MemberPanel memberPanel3 = new MemberPanel("123","huuu",image);
        MemberPanel memberPanel4 = new MemberPanel("hello","huuu",image);
        MemberPanel memberPanel5 = new MemberPanel("jane","huuu",image);
        MemberPanel memberPanel6 = new MemberPanel("dan","huuu",image);
        MemberPanel memberPanel7 = new MemberPanel("smith","huuu",image);
        MemberPanel memberPanel8 = new MemberPanel("jsckson","huuu",image);
        MemberPanel memberPanel9 = new MemberPanel("jsckson","huuu",image);
        MemberPanel memberPanel10 = new MemberPanel("jsckson","huuu",image);

        friendList.add(memberPanel);
        friendList.add(memberPanel2);
        friendList.add(memberPanel3);
        friendList.add(memberPanel4);
        friendList.add(memberPanel5);
        friendList.add(memberPanel6);
        friendList.add(memberPanel7);
        friendList.add(memberPanel8);
        friendList.add(memberPanel9);
        friendList.add(memberPanel10);

        for(MemberPanel panel:friendList){
            panel.setPreferredSize(new Dimension(this.getWidth()-5,50));
//            panel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            this.add(panel);
            panel.setVisible(false);
        }

        this.setVisible(true);
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
}
