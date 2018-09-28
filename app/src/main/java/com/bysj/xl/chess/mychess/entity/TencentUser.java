package com.bysj.xl.chess.mychess.entity;

/**
 * author:向磊
 * date:2018/9/22
 * Describe:腾讯用户实体类（qq,微信）
 */
public class TencentUser extends User {
    String QQopenID;
    int QQ;

    String WeChat;

    public String getQQopenID() {
        return QQopenID;
    }

    public void setQQopenID(String QQopenID) {
        this.QQopenID = QQopenID;
    }

    public int getQQ() {
        return QQ;
    }

    public void setQQ(int QQ) {
        this.QQ = QQ;
    }

    public String getWeChat() {
        return WeChat;
    }

    public void setWeChat(String weChat) {
        WeChat = weChat;
    }

}
