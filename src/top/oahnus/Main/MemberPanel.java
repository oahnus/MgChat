package top.oahnus.Main;

import top.oahnus.Bean.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by oahnus on 2016/7/1.
 */
public class MemberPanel extends JPanel {
    private JLabel memberName,memberInfo;
    private JLabel hasNewMsg;
    private JButton memberFigure;
    private Color old,hover;
    private User friend;
    private User user;
    private String friendID;
    private List<String> messages;

    MemberPanel(User friend,User user,Image figure){
        this.friend  = friend;
        this.user = user;

        friendID = friend.getUserID();

        messages = new ArrayList<>();

        memberName   = new JLabel();
        memberInfo   = new JLabel();
        hasNewMsg    = new JLabel();
        memberFigure = new JButton();

        memberName.setText(friend.getUsername());
        memberInfo.setText(friend.getInfo());
        memberFigure.setIcon(new ImageIcon(figure));

        hover = new Color(204,204,204);
        old = this.getBackground();

        setting();
        addListener();
    }

    private void addListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(old);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    //执行方法

//                    FriendsStateMonitor monitor = new FriendsStateMonitor(user);
//                    monitor.connectToServer();

                    ChatRoomFrame chatRoom = new ChatRoomFrame(user,friend);
                    Thread thread = new Thread(chatRoom);

                    thread.start();

System.out.println(messages.size());

                    if(!hasNewMsg.getText().equals("")) {
                        for (String str:messages) {
                            System.out.println(str);
                            chatRoom.setMessageArea(str);
                        }
                    }
                    hasNewMsg.setText("");
                }
            }
        });
    }

    private void setting() {
        setLayout(null);
        //设置控件的最大尺寸
        setMaximumSize(new Dimension(550,50));

        memberFigure.setBounds(5,5,40,40);
        memberName.setFont(new Font(getFont().getFontName(), Font.ITALIC,20));
        memberName.setBounds(60,5,100,25);

        hasNewMsg.setBounds(165,5,50,25);
        hasNewMsg.setFont(new Font(getFont().getFontName(), Font.ITALIC,15));

        memberInfo.setBounds(60,22,100,25);
        memberInfo.setFont(new Font(getFont().getFontName(), Font.ITALIC,15));

        //添加头像，昵称，简介
        add(memberFigure);
        add(memberName);
        add(memberInfo);
        add(hasNewMsg);

        setVisible(true);
        updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setHasNewMsg(String newMsg) {
        this.hasNewMsg.setText("<html><p color='red'>"+newMsg+"</p></html>");
    }

    public String getFriendID() {
        return friendID;
    }

    public List<String> getMessages() {
        return messages;
    }
}
