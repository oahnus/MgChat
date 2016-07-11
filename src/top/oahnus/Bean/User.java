package top.oahnus.Bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by oahnus on 2016/7/1.
 */

//保存登陆用户信息
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
    //头像
    private String figure;

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

    public Date getBornDate() {
        return born;
    }

    public void setBornDate(Date bornDate) {
        this.born = born;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String address) {
        this.address = address;
    }
}
