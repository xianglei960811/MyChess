package com.bysj.xl.chess.mychess.WebSocket.WsEvent;

/**
 * author:向磊
 * date:2019/1/8
 * Describe:修改密码模块，验证手机号账户是否存在事件
 */
public class WsForgetPhoneSuccessEvent<T> {
    T data;

    public WsForgetPhoneSuccessEvent(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
