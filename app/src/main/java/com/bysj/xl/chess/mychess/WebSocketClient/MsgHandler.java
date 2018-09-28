package com.bysj.xl.chess.mychess.WebSocketClient;

import android.content.Context;
import android.os.Looper;

import com.bysj.xl.chess.mychess.until.ToastUntil;

/**
 * author:向磊
 * date:2018/9/26
 * Describe:对response中msg消息进行统一处理
 */
public class MsgHandler {
    private ToastUntil myToast;

    private static final String LOG_SUCCESS = "log_success";
    private static final String LOG_PASSWORD_ERROR = "PassWord is Error";
    private static final String LOG_NO_USER = "no users";
    private static final String NO_QQ_USER = "NO_QQ_USER";
    public static final String USER_ALREADY_LOGIN = "user_already_login";

    public MsgHandler(Context mContext) {
        myToast = new ToastUntil(mContext);
    }

    public void msgHandler(String msg) {
        String message;
        switch (msg) {
            case LOG_NO_USER:
                message = "无此用户";
                break;
            case LOG_PASSWORD_ERROR:
                message = "账号或密码错误";
                break;
            case LOG_SUCCESS:
                message = "登录成功";
                break;
            case NO_QQ_USER:
                message = "无此QQ用户";
                break;
            case USER_ALREADY_LOGIN:
                message = "该用户已登录";
                break;
            default:
                message = msg;
                break;
        }
        Looper.prepare();
        myToast.ShowToastShort(message);
        Looper.loop();
    }
}
