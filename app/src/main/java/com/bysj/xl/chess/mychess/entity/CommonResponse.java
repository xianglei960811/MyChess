package com.bysj.xl.chess.mychess.entity;

import java.lang.reflect.Type;

/**
 * author:向磊
 * date:2018/9/17
 * Describe:
 */
public class CommonResponse<T> {
    private int code;
    private String dmsg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDmsg() {
        return dmsg;
    }

    public void setDmsg(String dmsg) {
        this.dmsg = dmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
