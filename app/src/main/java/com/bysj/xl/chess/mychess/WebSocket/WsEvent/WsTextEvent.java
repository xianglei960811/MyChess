package com.bysj.xl.chess.mychess.WebSocket.WsEvent;

/**
 * author:向磊
 * date:2018/12/21
 * Describe:接收消息事件
 */
public class WsTextEvent {
    String msg;

    public WsTextEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
