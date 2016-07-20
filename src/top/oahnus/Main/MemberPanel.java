package top.oahnus.Main;

import top.oahnus.Bean.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by oahnus on 2016/7/1.
 */
public class MemberPanel extends JPanel {
    private JLabel memberName,memberInfo;
    private JButton memberFigure;
    private Color old,hover;
    private User friend;
    private User user;

    MemberPanel(User friend,User user,Image figure){
        this.friend  = friend;
        this.user = user;
        memberName   = new JLabel();
        memberInfo   = new JLabel();
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

        memberInfo.setBounds(60,22,100,25);
        memberInfo.setFont(new Font(getFont().getFontName(), Font.ITALIC,15));

        //添加头像，昵称，简介
        add(memberFigure);
        add(memberName);
        add(memberInfo);

        setVisible(true);
        updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
