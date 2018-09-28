package com.bysj.xl.chess.mychess.entity;

/**
 * author:向磊
 * date:2018/9/21
 * Describe:用户信息基础类
 */
public class User {
    String usr_phone;
    String usr_age;
    String usr_AccountId;
    String usr_passWord;
    String usr_Sex;
    String usr_cretTime;
    String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUsr_phone() {
        return usr_phone;
    }

    public void setUsr_phone(String usr_phone) {
        this.usr_phone = usr_phone;
    }

    public String getUsr_age() {
        return usr_age;
    }

    public void setUsr_age(String usr_age) {
        this.usr_age = usr_age;
    }

    public String getUsr_AccountId() {
        return usr_AccountId;
    }

    public void setUsr_AccountId(String usr_AccountId) {
        this.usr_AccountId = usr_AccountId;
    }

    public String getUsr_passWord() {
        return usr_passWord;
    }

    public void setUsr_passWord(String usr_passWord) {
        this.usr_passWord = usr_passWord;
    }

    public String getUsr_Sex() {
        return usr_Sex;
    }

    public void setUsr_Sex(String usr_Sex) {
        this.usr_Sex = usr_Sex;
    }

    public String getUsr_cretTime() {
        return usr_cretTime;
    }

    public void setUsr_cretTime(String usr_cretTime) {
        this.usr_cretTime = usr_cretTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "usr_phone=" + usr_phone +
                ", usr_age=" + usr_age +
                ", usr_AccountId='" + usr_AccountId + '\'' +
                ", usr_passWord='" + usr_passWord + '\'' +
                ", usr_Sex='" + usr_Sex + '\'' +
                ", usr_cretTime='" + usr_cretTime + '\'' +
                '}';
    }
}
