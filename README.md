[TOC]

# MgChat
美聊，仿QQ

LoginFrame为程序入口类

## 包名解释
### Main 包中为程序主窗体界面及相关类
- ChatRoomFrame 聊天对话框窗体
- FriendsPanel 存放显示好友列表的Panel,panel放置在MainFrame窗体中
- MainFrame 主面板窗体
- MemberPanel 好友信息面板,用于在好友列表中显示好友昵称，签名等信息
- SearchFriendFrame 查找好友窗体
- UserPanel 好友信息Panel，用于显示查找好友的结果信息
### Util 工具类
- JDBCUtil 原使用JDBC链接数据库的工具类(可去除)
- MD5Util 加密工具类
- ProvAndCityFromJSON 从JSON中读取省市信息
- TimeUtil 时间工具类
### Bean JavaBean
- Message 消息Bean，封装好友消息及部分控制信息
- User 用户Bean ，封装用户信息
### lib 程序中使用的依赖jar
### ConnectToServer
- RecordReader 离线信息查询
### Extra
- Alert 用于显示提示信息
- SoftwareException 软件异常
### LoginIn
- LoginFrame 登陆窗体，程序的主入口
- LoginAuth 登陆验证
### config 程序配置
