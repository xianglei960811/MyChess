package com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent;

/**
 * author:向磊
 * date:2018/9/17
 * Describe:错误事件
 */
public class WebSocketErrorEvent {
    String msg;

    public WebSocketErrorEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
