package com.bysj.xl.chess.mychess.Constant;

import java.util.HashMap;
import java.util.Map;

public class C {
    public static final String BASE_PATH = "ws://192.168.1.14:100/MyChessServer/websocket";

    //sp标签
    public static final String IS_REMBER_NAME = "IsRember";
    public static final Boolean IS_REMBER = false;
    public static final String USER_NAME_NAME = "user_name";
    public static final String USER_NAME = "";
    public static final String PASS_WORD_NAME = "pass_word";
    public static final String PASS_WORD = "";
    public static final String USER_HEAD_NAME = "user_head";
    public static final String USER_HEAD = "";
    public static final String USER_PHONE_NAME = "user_phone";
    public static final String USER_PHONE = "";
    public static final String USER_GRADE_NAME = "user_grade";
    public static final String USER_GRADE = "";
    public static final String USER_QQ_NAME = "user_QQ";
    public static final String USER_QQ = "";
    public static final String USER_SEX_NAME = "user_sex";
    public static final String USER_SEX = "";
    public static final String USER_AGE_NAME = "user_age";
    public static final int USER_AGE_ = 0;
    public static final String USER_CREAT_TIME_NAME = "user_creat_time";
    public static final String USER_CREAT_TIME = "";

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

    //数据类型
    public static final String LoGING = "log";
    public static final String LOGIN_ACCONTID = "log_accoutID";
    public static final String LOGIN_PHONE = "log_Phone";
    public static final String LOGIN_QQ = "log_qq";

    public static final String SIGN_UP_MODE = "reg";

    public static final String FORGET_MODE = "for";
    public static final String FORGET_PHONE = "for_phone";//忘记密码模块中，验证手机是否存在标志
    public static final String FORGET_PASSWORD = "for_pass";//忘记密码模块中，修改密码标志

    public static final String user_MODE = "use";
    public static final String USE_ALL = "use_all";//个人中心信息修改标志
    public static final String USE_PHONE = "use_phone";
    //错误
    public static final String NO_QQ_USER = "NO_QQ_USER";


}
