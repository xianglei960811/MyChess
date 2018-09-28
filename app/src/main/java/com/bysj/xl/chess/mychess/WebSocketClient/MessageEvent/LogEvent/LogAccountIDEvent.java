package com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.LogEvent;

/**
 * author:向磊
 * date:2018/9/26
 * Describe:
 */
public class LogAccountIDEvent<T> {
    T data;

    public LogAccountIDEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
