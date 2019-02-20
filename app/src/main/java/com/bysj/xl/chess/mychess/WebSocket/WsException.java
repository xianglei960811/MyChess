package com.bysj.xl.chess.mychess.WebSocket;

import android.util.Log;

import com.bysj.xl.chess.mychess.Application.App;
import com.bysj.xl.chess.mychess.until.ToastUntil;

/**
 * author:向磊
 * date:2018/12/21
 * Describe:服务器返回错误异常处理类
 */
public class WsException extends RuntimeException {
    static ToastUntil toastUntil = new ToastUntil(App.getContext()) ;
    private static final String PASS_ERROR = "PassWord is Error";
    private static final String NO_USER = "no users";
    private static final String NO_QQ_USER = "NO_QQ_USER";
    private static final String USER_ALREADY_LOGING = "user_already_login";

    private static final String REG_HAVE_PHONE = "hava this phone";
    private static final String REG_HAVE_NAME = "hava this name";

    private static final String FORGET_NO_PHONE = "no phone";
    private static final String FORGET_PASS_FAIL = "modify pass fail";

    public WsException(String message) {
        super(getWsExceptionMessage(message));
    }

    private static String getWsExceptionMessage(String message) {

        String msg = message;
        switch (msg) {
            case PASS_ERROR:
                msg = "密码错误";
                break;
            case NO_USER:
                msg = "无此用户，请注册";
                break;
            case NO_QQ_USER:
                msg = "无此QQ用户，请绑定已有账号或注册";
                break;
            case USER_ALREADY_LOGING:
                msg = "该账号已登录";
                break;
            case REG_HAVE_PHONE:
                msg = "此手机号已注册";
                break;
            case REG_HAVE_NAME:
                msg = "此用户名已存在";
                break;
            case FORGET_NO_PHONE:
                msg = "无此手机用户";
                break;
            case FORGET_PASS_FAIL:
                msg = "修改密码失败";
                break;
            default:
                break;
        }
        Log.e("Aiexception", "getWsExceptionMessage: " + msg);
        toastUntil.ShowToastShort(msg);
        return msg;
    }
}
