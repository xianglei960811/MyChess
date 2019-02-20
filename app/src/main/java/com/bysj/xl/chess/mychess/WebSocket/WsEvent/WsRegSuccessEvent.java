package com.bysj.xl.chess.mychess.WebSocket.WsEvent;

/**
 * author:向磊
 * date:2018/12/21
 * Describe:注册成功事件
 */
public class WsRegSuccessEvent<T> {
    T data;

    public WsRegSuccessEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
