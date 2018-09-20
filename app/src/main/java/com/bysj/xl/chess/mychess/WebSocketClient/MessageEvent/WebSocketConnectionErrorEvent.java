package com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent;

/**
 * author:向磊
 * date:2018/9/18
 * Describe:连接失败事件
 */
public class WebSocketConnectionErrorEvent {
    String msg;

    public WebSocketConnectionErrorEvent(String msg) {
        this.msg = msg;
    }
}
