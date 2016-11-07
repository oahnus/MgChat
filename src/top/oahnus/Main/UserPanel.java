package top.oahnus.Main;

import top.oahnus.Bean.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by oahnus on 2016/7/28.
 */
public class UserPanel extends JPanel{
//    public static final String SERVER_IP = "127.0.0.1";
    public static final String SERVERIP = "139.129.49.14";

    private JLabel figure;
    private JLabel info;
    private JButton addButton;
    private Font font = new Font("Microsoft YaHei",Font.PLAIN,18);
    private User selfUser,friendUser;

    public UserPanel(User selfUser,User friendUser){
        this.selfUser = selfUser;
        this.friendUser = friendUser;
        figure = new JLabel();
        info = new JLabel();
        addButton = new JButton("加好友");

    }

    public void setting(Image icon) {
        setSize(600,50);
        setMaximumSize(new Dimension(600,50));

        figure.setBounds(5,5,40,40);
        figure.setIcon(new ImageIcon(icon));

        info.setFont(font);
        info.setBounds(50,5,400,40);
        info.setText(friendUser.getUsername()+"("+friendUser.getUserID()+") "+friendUser.getSex()+" "+friendUser.getAddress());

        addButton.setBounds(500,10,90,30);
        addButton.setFont(font);

        add(figure);
        add(info);
        add(addButton);

        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket(SERVERIP,7887);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("addFriend","");
                    map.put("userid",selfUser.getUserID());
                    map.put("friendid",friendUser.getUserID());

                    oos.writeObject(map);
                    oos.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
