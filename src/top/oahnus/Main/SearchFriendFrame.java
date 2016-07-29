package top.oahnus.Main;

import top.oahnus.Bean.User;
import top.oahnus.Util.ProvAndCityFromJSON;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.List;

/**
 * Created by oahnus on 2016/7/27.
 */

/**
 * 按条件查找好友的窗口类
 */
public class SearchFriendFrame extends JFrame {

//    public static final String SERVERIP = "127.0.0.1";
    public static final String SERVERIP = "139.129.49.14";

    /**
     * 控件定义
     */
    //提交Button
    private JButton searchButton;
    //自身引用
    private SearchFriendFrame self = this;
    //程序图标
    private Image icon;
    //serach图标
    private Image searchIcon;
    //省市下拉框
    private JComboBox provComboBox;
    private JComboBox cityComboBox;
    //性别下拉框
    private JComboBox sexComboBox;
    //账号，昵称输入框
    private JTextField userIDField;
    private JTextField usernameField;
    //查找条件Panel
    private JPanel conditionPanel;
    //结果Panel
    private JPanel resultPanel;
    private JScrollPane jScrollPane;
    //提示标签
    private JLabel label1,label2,label3,label4;
    //字体
    private Font font;

    //工具类,从JSON文件中读取省市信息
    private ProvAndCityFromJSON provAndCityFromJSON;
    //用于与服务器通信，获取好友查询结果
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User selfAccount;

    public SearchFriendFrame(User user){
        this.selfAccount = user;
        font = new Font("Microsoft YaHei",Font.PLAIN,14);

        label1 = new JLabel("按账号:");
        label2 = new JLabel("按昵称:");
        label3 = new JLabel("地区:");
        label4 = new JLabel("性别:");
        conditionPanel = new JPanel();
        resultPanel = new JPanel();
        jScrollPane = new JScrollPane(resultPanel);

        searchButton = new JButton();
        userIDField = new JTextField(20);
        usernameField = new JTextField(20);
        sexComboBox = new JComboBox(new String[]{"不限","男","女"});
        provComboBox = new JComboBox(new String[]{"不限"});
        cityComboBox = new JComboBox(new String[]{"不限"});

        /**
         * 加载JSON文件，读取省市信息添加到ComboBox中
         */
        provAndCityFromJSON = new ProvAndCityFromJSON();
        provAndCityFromJSON.loadJSONFile();
    }

    /**
     * 登陆窗体
     */
    public void LaunchFrame(){
        readImage();
        setting();
        addListener();
    }

    /**
     * 窗体及控件设置
     */
    private void setting(){
        /**
         * 窗体设置
         */
        setLayout(null);
        setSize(600,500);
        setResizable(false);
        setLocation(400,200);
        setTitle("查找好友");
        setFont(font);

        setIconImage(icon);

        /**
         * 控件设置
         */
        conditionPanel.setBackground(new Color(46, 204, 113));
        conditionPanel.setBounds(0,0,600,100);
        conditionPanel.setLayout(null);

//        resultPanel.setBackground(new Color(46, 204, 113));
        resultPanel.setBackground(Color.LIGHT_GRAY);
        resultPanel.setBounds(0,100,600,400);
        resultPanel.setLayout(null);
//        resultPanel.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(0,100,600,400);
        jScrollPane.setAutoscrolls(true);
        /**
         * 账号，昵称条件
         */
        label1.setBounds(10,10,50,25);
        label1.setFont(font);
        userIDField.setBounds(65,10,150,25);
        usernameField.setFont(font);
        label2.setBounds(230,10,50,25);
        label2.setFont(font);
        usernameField.setBounds(285,10,150,25);
        usernameField.setFont(font);

        /**
         * 地区，性别条件
         */
        label3.setBounds(10,40,40,25);
        label3.setFont(font);
        provComboBox.setBounds(60,40,100,30);
        provComboBox.setFont(font);
        cityComboBox.setBounds(170,40,100,30);
        cityComboBox.setFont(font);
        label4.setBounds(280,40,40,25);
        label4.setFont(font);
        sexComboBox.setBounds(320,40,100,30);
        sexComboBox.setFont(font);

        /**
         * 发送按钮
         */
        searchButton.setBounds(450,30,100,35);
        searchButton.setIcon(new ImageIcon(searchIcon));


        conditionPanel.add(label1);
        conditionPanel.add(userIDField);
        conditionPanel.add(label2);
        conditionPanel.add(usernameField);

        conditionPanel.add(label3);
        conditionPanel.add(provComboBox);
        conditionPanel.add(cityComboBox);
        conditionPanel.add(label4);
        conditionPanel.add(sexComboBox);

        conditionPanel.add(searchButton);

        /**
         * 添加控件到窗体
         */
        getContentPane().add(conditionPanel);
        getContentPane().add(jScrollPane);

        setVisible(true);

        /**
         * 从文件加载省份信息
         */
        String[] provs = provAndCityFromJSON.getProvs();
        for(int i=0;i<provs.length;i++){
            provComboBox.addItem(provs[i]);
        }

    }

    /**
     * 为控件添加监听器
     */
    private void addListener(){
        self.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                self.dispose();
            }
        });

        /**
         * 对省份下拉框添加监听器，当省份改变时，读取城市信息
         */
        provComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selectedProv = provComboBox.getSelectedItem().toString();
                if(selectedProv.equals("不限")){
                    cityComboBox.removeAllItems();
                    cityComboBox.addItem("不限");
                }else{
                    cityComboBox.removeAllItems();
                    String[] city = provAndCityFromJSON.getCitys(selectedProv);
                    for(int i=0;i<city.length;i++){
                        cityComboBox.addItem(city[i]);
                    }
                }
                resultPanel.removeAll();
                resultPanel.repaint();
            }
        });

        cityComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                resultPanel.removeAll();
                resultPanel.repaint();
            }
        });

        sexComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                resultPanel.removeAll();
                resultPanel.repaint();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new Socket(SERVERIP,8887);
                    ois = new ObjectInputStream(socket.getInputStream());
                    oos = new ObjectOutputStream(socket.getOutputStream());

                    //保存查找条件
                    User user = new User();
                    user.setUserID(userIDField.getText());
                    user.setUsername(usernameField.getText());
                    user.setAddress(provComboBox.getSelectedItem().toString()+cityComboBox.getSelectedItem().toString());
                    user.setSex(sexComboBox.getSelectedItem().toString());

                    Map<String,User> map = new HashMap<String, User>();
                    map.put("findFriend",user);

                    oos.writeObject(map);
                    oos.flush();

                    java.util.List<User> retUsers = new ArrayList<User>();
                    retUsers = (List<User>) ois.readObject();

//                    resultPanel = new JPanel();
                    resultPanel.setBackground(Color.LIGHT_GRAY);

                    if(retUsers != null){
                        System.out.println("找到"+retUsers.size()+"用户");
                        //计数
                        int num = 0;

                        for(int i=0;i<retUsers.size();i++){
                            String userID = retUsers.get(i).getUserID();
                            if(!selfAccount.getUserID().equals(userID)) {
                                UserPanel panel = new UserPanel(selfAccount,retUsers.get(i));
                                resultPanel.add(panel);
                                panel.setBounds(0, 50 * num, 600, 50);
                                panel.setting(icon);
                                num++;
                            }
                        }
                    }else{

                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * 读取图片资源
     */
    private void readImage(){
        try {
            icon = ImageIO.read(new File("resource/icon/icon.jpg"));
            searchIcon = ImageIO.read(new File("resource/icon/search.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试
     */
//    public static void main(String[] args){
//        new SearchFriendFrame().LaunchFrame();
//    }
}
