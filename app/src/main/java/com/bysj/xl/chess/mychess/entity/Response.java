package com.bysj.xl.chess.mychess.entity;

/**
 * author:向磊
 * date:2018/9/23
 * Describe:服务器消息统一实体类
 */
public class Response<T> {
    String usr_response_type;
    String msg;
    T data;

    public String getUsr_response_type() {
        return usr_response_type;
    }

    public void setUsr_response_type(String usr_response_type) {
        this.usr_response_type = usr_response_type;
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
