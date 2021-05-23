package com.example.activitytest.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

public class UserInfo1 extends LitePalSupport {
    private String userName;                  //用户名
    private String userPwd;                   //用户密码
    private long Id;                         //用户ID号
    private String person;
    private String QQ;
    private String Phone;
    private String motto;
    private String username1;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getPersonal() {
        return person;
    }
    public void setPersonal(String person) {
        this.person = person;

    }

    public String getQQ() {
        return QQ;
    }
    public void setQQ(String QQ) {
        this.QQ = QQ;

    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String Phone) {
        this.Phone = Phone;

    }

    public String getmotto() {
        return motto;
    }
    public void setmotto(String motto) {
        this.motto = motto;

    }

    public String getUsername() {
        return username1;
    }

    public void setUsername(String username) {
        this.username1 = username;
    }


    public UserInfo1(String userName, String userPwd, long id) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.Id = Id;
    }

    public UserInfo1() {
    }
}

