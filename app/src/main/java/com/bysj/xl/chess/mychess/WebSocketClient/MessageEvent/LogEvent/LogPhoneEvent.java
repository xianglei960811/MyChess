package com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.LogEvent;

/**
 * author:向磊
 * date:2018/9/26
 * Describe:登录事件
 */
public class LogPhoneEvent<T> {
    T data;

    public LogPhoneEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
