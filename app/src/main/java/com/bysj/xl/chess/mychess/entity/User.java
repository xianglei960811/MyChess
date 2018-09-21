package com.bysj.xl.chess.mychess.entity;

/**
 * author:向磊
 * date:2018/9/21
 * Describe:用户信息基础类
 */
public class User {
    int usr_phone;
    int usr_age;
    int usr_QQ;
    String usr_AccountId;
    String usr_passWord;
    String usr_request_type;//请求类型

    String usr_weChat;

    String usr_Sex;
    String usr_cretTime;

    public int getUsr_phone() {
        return usr_phone;
    }

    public void setUsr_phone(int usr_phone) {
        this.usr_phone = usr_phone;
    }

    public int getUsr_age() {
        return usr_age;
    }

    public void setUsr_age(int usr_age) {
        this.usr_age = usr_age;
    }

    public int getUsr_QQ() {
        return usr_QQ;
    }

    public void setUsr_QQ(int usr_QQ) {
        this.usr_QQ = usr_QQ;
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

    public String getUsr_request_type() {
        return usr_request_type;
    }

    public void setUsr_request_type(String usr_request_type) {
        this.usr_request_type = usr_request_type;
    }

    public String getUsr_weChat() {
        return usr_weChat;
    }

    public void setUsr_weChat(String usr_weChat) {
        this.usr_weChat = usr_weChat;
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
                ", usr_QQ=" + usr_QQ +
                ", usr_AccountId='" + usr_AccountId + '\'' +
                ", usr_passWord='" + usr_passWord + '\'' +
                ", usr_request_type='" + usr_request_type + '\'' +
                ", usr_weChat='" + usr_weChat + '\'' +
                ", usr_Sex='" + usr_Sex + '\'' +
                ", usr_cretTime='" + usr_cretTime + '\'' +
                '}';
    }
}
