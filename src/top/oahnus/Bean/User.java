package top.oahnus.Bean;

import java.util.Date;

/**
 * Created by oahnus on 2016/7/1.
 */

//保存登陆用户信息
public class User {
    //账号
    private String userID;
    //昵称
    private String username;
    //密码
    private String password;
    //简介
    private String info;
    //出生日期
    private Date bornDate;
    //性别
    private String sex;
    //所在地
    private String adress;
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
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
