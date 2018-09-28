package com.bysj.xl.chess.mychess.entity;

/**
 * author:向磊
 * date:2018/9/22
 * Describe:请求数据实体类
 */
public class Request<T> {
    String usr_request_type;//请求类型
    T data;//数据
    String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setUsr_request_type(String usr_request_type) {
        this.usr_request_type = usr_request_type;
    }

    public String getUsr_request_type() {
        return usr_request_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
