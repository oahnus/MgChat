package top.oahnus.Main;

import top.oahnus.Bean.Message;
import top.oahnus.Bean.User;
import top.oahnus.Util.TimeUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created by oahnus on 2016/7/12.
 */
public class ChatRoomFrame extends JFrame implements Runnable{

    private final String SERVERIP = "127.0.0.1";

    //显示文本信息
    private JTextArea messageArea = new JTextArea();
    //输入信息
    private JTextArea inputArea   = new JTextArea();
    //关闭
//    private JLabel close          = new JLabel();
    private JButton close         = new JButton();
    //最小化
    private JLabel min            = new JLabel();
    //最大化
    private JLabel max            = new JLabel();
    //标题，功能按钮
    private JPanel titlePanel     = new JPanel();
    private JButton closeButton   = new JButton("关闭");
    private JButton sendButton    = new JButton("发送");

    private JButton figureImage   = new JButton();
    private JLabel username       = new JLabel();
    private JLabel userInfo       = new JLabel();

    private JScrollPane scrollPane = new JScrollPane(messageArea);

    private Socket socket         = null;

    private ObjectInputStream ois   = null;
    private ObjectOutputStream oos  = null;

    private Image maxIcon         = null;
    private Image minIcon         = null;
    private Image closeIcon       = null;
    private Image returnIcon      = null;

    private ChatRoomFrame self    = this;
    //记录最大化点击次数
    private long clickNum         = 0;

    private int mouseX            = 0;
    private int mouseY            = 0;
    private boolean isCanMoved    = false;
    private boolean isRunning         = false;
    private String ip = "";

    public static final int WINDOWWIDTH  = 600;
    public static final int WINDOWHEIGHT = 520;

    private User friend;
    private User user;
    //输入框与信息框的字体
    private Font font = new Font("Microsoft YaHei",Font.PLAIN,14);
    private Thread thread;
    private Server server;

    public ChatRoomFrame(User user,User friend){
        this.friend = friend;
        this.user = user;
    }

    //初始化
    private void init(){
        setting();
        addListener();
        connectToServer();
    }

    //设置
    private void setting(){
        setLayout(null);
        setSize(WINDOWWIDTH,WINDOWHEIGHT);
//        setResizable(false);
        setLocation(300,100);
        setUndecorated(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        readImage();
        if(friend.getFigureImage()!=null) {
            setIconImage(friend.getFigureImage());
        }
        settingTitlePanel();

        titlePanel.setBounds(0,0,WINDOWWIDTH,65);
        titlePanel.setBackground(Color.BLUE);

        messageArea.setBounds(0,0,WINDOWWIDTH,290);
        messageArea.setLineWrap(true);
        messageArea.setEditable(false);
        messageArea.setFont(font);

        inputArea.setBounds(0,400,WINDOWWIDTH,90);
        inputArea.setLineWrap(true);
        inputArea.setFont(font);
        inputArea.setFocusable(true);

        closeButton.setBounds(WINDOWWIDTH-140,490,65,25);
        sendButton.setBounds(WINDOWWIDTH-70,490,65,25);

        //消息框滚动条
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAutoscrolls(true);
        scrollPane.setBounds(0,90,WINDOWWIDTH,290);

        getContentPane().add(titlePanel);
        getContentPane().add(scrollPane);
        getContentPane().add(inputArea);
        getContentPane().add(closeButton);
        getContentPane().add(sendButton);

        setVisible(true);
    }

    private void settingTitlePanel(){
        titlePanel.setLayout(null);

        //设置图标
        max.setIcon(new ImageIcon(maxIcon));
        min.setIcon(new ImageIcon(minIcon));
        close.setIcon(new ImageIcon(closeIcon));

        close.setBounds(WINDOWWIDTH-20,0,20,20);
        max.setBounds(WINDOWWIDTH-40,0,20,20);
        max.setOpaque(false);
        min.setBounds(WINDOWWIDTH-60,0,20,20);
        min.setOpaque(false);

        figureImage.setBounds(10,5,50,50);
        username.setText(friend.getUsername());
        username.setBounds(80,5,100,30);
        username.setFont(new Font("Microsoft YaHei", Font.PLAIN,20));

        userInfo.setText(friend.getInfo());
        userInfo.setBounds(80,34,self.getWidth()-80,30);
        userInfo.setFont(new Font("Microsoft YaHei", Font.PLAIN,12));

        titlePanel.add(figureImage);
        titlePanel.add(username);
        titlePanel.add(userInfo);
        titlePanel.add(close);
        titlePanel.add(min);
        titlePanel.add(max);

    }

    //添加监听器
    private void addListener(){
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                disConnect();
                self.dispose();
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disConnect();
                self.dispose();
            }
        });
        //添加无边框窗体的拖动功能
        titlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                if(mouseY>titlePanel.getHeight()){
                    isCanMoved = false;
                }else{
                    isCanMoved = true;
                }
            }
        });
        titlePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isCanMoved){
                    //获取鼠标在屏幕中的坐标
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();

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
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disConnect();
System.out.println("发送关闭");
                self.dispose();
            }
        });
        max.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickNum++;
                /**
                 * 全屏窗体
                 */
                if(clickNum%2!=0){
                    /**
                     * 设置窗体全屏的另外方式
                     * GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
                     * GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
                     * graphicsDevice.setFullScreenWindow(self);
                     */
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                    self.setSize(dimension.width,dimension.height);
                    self.setLocation(0,0);
                    max.setIcon(new ImageIcon(returnIcon));

                    closeButton.setBounds(self.getWidth()-140,self.getHeight()-30,65,25);
                    sendButton.setBounds(self.getWidth()-70,self.getHeight()-30,65,25);
                    close.setBounds(self.getWidth()-20,0,20,20);
                    min.setBounds(self.getWidth()-60,0,20,20);
                    max.setBounds(self.getWidth()-40,0,20,20);

                    titlePanel.setBounds(0,0,self.getWidth(),65);
                    inputArea.setBounds(0,self.getHeight()-120,self.getWidth(),90);
                    messageArea.setBounds(0,0,self.getWidth(),self.getHeight()-230);

                    scrollPane.setBounds(0,90,self.getWidth(),self.getHeight()-230);
                }
                /**
                 * 还原窗体
                 */
                else{
                    self.setSize(WINDOWWIDTH,WINDOWHEIGHT);
                    max.setIcon(new ImageIcon(maxIcon));
                    setLocation(300,100);

                    closeButton.setBounds(self.getWidth()-140,self.getHeight()-30,65,25);
                    sendButton.setBounds(self.getWidth()-70,self.getHeight()-30,65,25);
                    close.setBounds(self.getWidth()-20,0,20,20);
                    min.setBounds(self.getWidth()-60,0,20,20);
                    max.setBounds(self.getWidth()-40,0,20,20);

                    titlePanel.setBounds(0,0,self.getWidth(),65);
                    inputArea.setBounds(0,self.getHeight()-120,self.getWidth(),90);
                    messageArea.setBounds(0,0,self.getWidth(),self.getHeight()-230);
                    scrollPane.setBounds(0,90,self.getWidth(),self.getHeight()-230);

                }
            }
        });
        min.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                self.setExtendedState(JFrame.MAXIMIZED_BOTH);//窗体最大化
                self.setExtendedState(JFrame.ICONIFIED);
            }
        });

        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
System.out.println("##发送成功");
                    sendMessage();
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        self.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disConnect();
                self.dispose();
            }
        });
    }

    /**
     * 发送消息，并将消息显示在聊天对话框中
     */
    public void sendMessage(){
        Message msg = new Message();
        msg.setCode("MSG");
        msg.setContent(inputArea.getText().trim());
        msg.setTargetID(friend.getUserID());
        msg.setSourceID(user.getUserID());

        messageArea.setText(messageArea.getText()+ TimeUtil.getTimeNow()+" "+user.getUsername()+" : "+msg.getContent()+"\n");
        inputArea.setText("");

        try {
            oos.writeObject(msg);
System.out.println("##发送成功");
        } catch (IOException e1) {
            e1.printStackTrace();
System.out.println("##消息发送失败");
        }
    }

    /**
     * 连接聊天服务器
     */
    public void connectToServer(){
        try {
            socket=new Socket(SERVERIP,8888);

            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
            isRunning=true;

            Message loginMsg = new Message();
            loginMsg.setCode("CHATIN");
            loginMsg.setContent(user.getUserID());

            oos.writeObject(loginMsg);
            oos.flush();

        } catch (IOException e) {
            System.out.println("##链接出错");
        }

        //将server的定义放在前面，方便在结束窗体时关闭接收server信息
        server = new Server();
        thread = new Thread(server);
        thread.start();
System.out.println("##服务端创建成功");
    }

    /**
     * 关闭连接
     */
    private void disConnect() {
        isRunning=false;
        Message closeMsg = new Message();
        closeMsg.setCode("CLOSE");
        try {
            oos.writeObject(closeMsg);
        } catch (IOException e) {
            e.printStackTrace();
System.out.println("关闭信息发送失败");
        }
        try{
            socket.shutdownOutput();
            socket.shutdownInput();
            if(oos!=null){
                oos.close();
                oos = null;
            }
            if(ois!=null){
                ois.close();
                ois = null;
            }
            if(socket!=null){
                socket.close();
                socket = null;
            }

            thread.stop();
            server = null;
            thread = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取图片资源
     */
    private void readImage(){
        try {
            maxIcon   = ImageIO.read(new File("resource/max.png"));
            minIcon   = ImageIO.read(new File("resource/sub.png"));
            closeIcon = ImageIO.read(new File("resource/close.png"));
            returnIcon = ImageIO.read(new File("resource/max2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
    }

    /**
     * 在聊天对话框中添加新的消息
     * @param msg 新消息
     */
    public void setMessageArea(String msg){
        StringBuffer sb = new StringBuffer();
        sb.append(messageArea.getText()).append(friend.getUsername()).append(" : ").append(msg).append("\n");
        messageArea.setText(sb.toString());
        messageArea.setCaretPosition(messageArea.getText().length());
    }

    /**
     * Server内部类,通过此类来与Server端发送接收消息
     */
    class Server implements Runnable{

        @Override
        public void run() {
            Message msg;
            try{
                while(isRunning){
System.out.println("准备接受服务器消息");
                    msg = (Message) ois.readObject();
System.out.println("接收消息成功");

System.out.println(msg.getContent());

                    messageArea.setText(messageArea.getText()+ TimeUtil.getTimeNow()+" "+friend.getUsername()+" : "+msg.getContent()+"\n");
                    messageArea.setCaretPosition(messageArea.getText().length());
                }
            } catch (IOException e) {
                e.printStackTrace();
System.out.println("读取错误");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
