package top.oahnus.LoginIn;

import top.oahnus.Bean.User;
import top.oahnus.Extra.Alert;
import top.oahnus.Main.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.io.File;
import java.io.IOException;

/**
 * Created by oahnus on 2016/6/16.
 */

//登录界面主窗体
public class LoginFrame extends JFrame {

    private final String REGISTER_URL = "http://139.129.49.14/mgchat/register.html";
    private final String FORGET_URL = "http://139.129.49.14";

    //用户账号输入框
    private JTextField userIDField;
    //密码输入框
    private JPasswordField passwordField;
    private JLabel backLabel, figureLabel;
    private JButton loginButton;

    private JLabel forgetLabel, registerLabel;

    private JFrame self = this;

    private Font font = null;

    private User user;

    LoginFrame(){
        backLabel     = new JLabel();
        userIDField   = new JTextField("10000");//test账号
        passwordField = new JPasswordField("123456");//test账号

        figureLabel   = new JLabel();
        loginButton   = new JButton("<html>登  陆</html>");
        forgetLabel   = new JLabel("<html><u>忘记密码</u><html>");
        registerLabel = new JLabel("<html><u>注册账号</u><html>");

        font = new Font("Courier New",Font.PLAIN,14);
    }

    public void LaunchFrame(){
        setting();
        addListener();
    }

    private void addListener() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginAuth loginAuth = new LoginAuth();
                String userID       = userIDField.getText();
                String password     = passwordField.getText();
                user                = loginAuth.verify(userID,password);

                //使用user传送数据可能有局限性？
                //TODO
                if(user!=null){
System.out.println("success");

                    MainFrame mainFrame = new MainFrame(user);
                    mainFrame.launch();
                    close();
                }else{
System.out.println("error");
                    Alert alert = new Alert("用户名或密码错误!!!");
                    alert.showAlert();
                }
            }
        });

        registerLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(Color.blue);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                URI uri = URI.create(REGISTER_URL);

                Desktop desktop = Desktop.getDesktop();

                if(desktop.isSupported(Desktop.Action.BROWSE)){
                    try {
                        desktop.browse(uri);
                    } catch (IOException e1) {
                        //无法获取浏览器对象
                        e1.printStackTrace();
                    }
                }
            }
        });

        forgetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                URI uri = URI.create(FORGET_URL);

                Desktop desktop = Desktop.getDesktop();

                if(desktop.isSupported(Desktop.Action.BROWSE)){
                    try {
                        desktop.browse(uri);
                    } catch (IOException e1) {
                        //无法获取浏览器对象
                        //e1.printStackTrace();
                        //TODO 添加提示
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                forgetLabel.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                forgetLabel.setForeground(Color.blue);
            }
        });
    }

    private void setting(){
        setLayout(null);
        setResizable(false);
        setTitle("MgChat");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //设置位置，宽高
        setBounds(420,200,430,330);

        backLabel.setBounds(0,0,430,150);
        userIDField.setBounds(115,160,200,30);
        passwordField.setBounds(115,195,200,30);
        figureLabel.setBounds(10,160,100,100);
        registerLabel.setBounds(320,160,60,30);
        forgetLabel.setBounds(320,195,60,30);
        loginButton.setBounds(130,260,160,30);

        //设置图标
        Image icon = null;
        Image back = null;
        try {
            icon = ImageIO.read(new File("resource/icon/icon.jpg"));
            back = ImageIO.read(new File("resource/icon/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setIconImage(icon);
        Image backImage = back.getScaledInstance(backLabel.getWidth(),backLabel.getHeight(),Image.SCALE_DEFAULT);
        backLabel.setIcon(new ImageIcon(backImage));
        Image figure    = icon.getScaledInstance(figureLabel.getWidth(),figureLabel.getHeight(),Image.SCALE_DEFAULT);
        figureLabel.setIcon(new ImageIcon(figure));

        //设置字体
        userIDField.setFont(font);
        registerLabel.setFont(font);
        forgetLabel.setFont(font);
        loginButton.setFont(font);

        forgetLabel.setForeground(Color.blue);
        registerLabel.setForeground(Color.blue);

        //添加控件
        getContentPane().add(backLabel);
        getContentPane().add(figureLabel);
        getContentPane().add(registerLabel);
        getContentPane().add(loginButton);
        getContentPane().add(userIDField);
        getContentPane().add(passwordField);
        getContentPane().add(forgetLabel);

        setFocusable(true);

        setVisible(true);
    }

    /**
     * 渐隐退出？测试
     */
    private void close(){
        int width = self.getWidth();
        int height = self.getHeight();
        while(height>0){
            self.setSize(width,height);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            height-=20;
        }
        self.dispose();
    }

    public static void main(String[] args){
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.LaunchFrame();

    }
}
