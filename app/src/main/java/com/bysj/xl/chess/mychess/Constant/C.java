package com.bysj.xl.chess.mychess.Constant;

public class C {
    public static final String BASE_PATH = "ws://10.120.4.183:100/MyService/websocket";

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
}
