package com.bysj.xl.chess.mychess.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:向磊
 * date:2018/9/21
 * Describe:检查手机号或密码是否符合规范
 */
public class ReCheck {
    public static Boolean checkPassWord(String pass){
        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,12}$");
        Matcher m = p.matcher(pass);
        return m.matches();
    }

    public static Boolean checkPhoneNum(String phone){
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
