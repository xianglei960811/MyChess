package com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.LogEvent;

/**
 * author:向磊
 * date:2018/9/26
 * Describe:
 */
public class LogQQEvent<T> {
    String msg;
    T data;

    public LogQQEvent(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
