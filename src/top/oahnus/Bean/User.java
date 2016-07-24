package top.oahnus.Bean;

import java.awt.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by oahnus on 2016/7/4.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    //账号
    private String userID;
    //昵称
    private String username;
    //密码
    private String password;
    //简介
    private String info;
    //出生日期
    private Date born;
    //性别
    private String sex;
    //所在地
    private String address;
    //头像存储路径
    private String figure;
    //头像
    private Image figureImage;
    //好友列表
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private List<User> friendsList;

    public  Image getFigureImage() {
        return figureImage;
    }

    public void  setFigureImage(Image figureImage) {
        this.figureImage = figureImage;
    }

    public List<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<User> friendsList) {
        this.friendsList = friendsList;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
