package com.bysj.xl.chess.mychess.Constant;

public class C {
    public static final String BASE_PATH = "ws://10.120.4.115:100/MyChessServer/websocket";

    //sp标签
    public static final String IS_REMBER_NAME = "IsRember";
    public static final Boolean IS_REMBER = false;
    public static final String USER_NAME_NAME="user_name";
    public static final String USER_NAME = "admin";
    public static final String PASS_WORD_NAME = "pass_word";
    public static final String PASS_WORD = "123";

    //谁赢的常量
    public static final int ISWIN_NOT_WIN = 0;
    public static final int ISWIN_WHRITE_WIN = 1;
    public static final int ISWIN_BLACK_WIN = 2;

    //选中标志所在位子
    public static final String SIGN_MODE_NAME = "SIGN";
    public static final int SIGN_UP = 1;
    public static final int SIGN_DOWN = 2;
    public static final int SIGN_LEFT = 3;
    public static final int SIGN_RIGHT = 4;

    public static final int RECONNECT_TIME = 5;//重连次数

    //登录类型
    public static final String LOGIN_ACCONTID = "log_accoutID";
    public static final String LOGIN_PHONE = "log_Phone";
    public static final String LOGIN_QQ= "log_qq";
    public static final String LOGIN_WECHAT= "log_weChat";
    //错误
    public static final String NO_QQ_USER = "NO_QQ_USER";
}
