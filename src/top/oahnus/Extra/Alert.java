package top.oahnus.Extra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by oahnus on 2016/7/4.
 */
public class Alert extends JFrame {

    private String message;
    private JButton confirm = new JButton("OK");
    private JLabel msg = new JLabel();
    private Alert self = this;

    public Alert(){}

    public Alert(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void showAlert(){
        setLayout(null);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(200,100);
        setLocation(size.width/2-100,size.height/2-50);
        setResizable(false);
        setUndecorated(true);

        confirm.setBounds(70,65,60,25);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        msg.setText(message);
        msg.setBounds(20,10,180,20);

        getContentPane().add(msg);
        getContentPane().add(confirm);

        setVisible(true);
    }

    public static void main(String[] args){
        Alert alert = new Alert("无法连接到服务器");
        alert.showAlert();
    }
}
