package com.bysj.xl.chess.mychess.WebSocketClient;

/**
 * author:向磊
 * date:2018/9/23
 * Describe:response回调接口
 */
public interface ResponseCallBack<T> {
    void onResponse(String type,T t);
    void onResponseMsg(String msg);
}
