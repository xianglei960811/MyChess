package com.bysj.xl.chess.mychess.WebSocket.WsEvent;

/**
 * author:向磊
 * date:2018/12/21
 * Describe:登录成功事件
 */
public class WsLogSuccessEvent<T> {
    T data;

    public WsLogSuccessEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
