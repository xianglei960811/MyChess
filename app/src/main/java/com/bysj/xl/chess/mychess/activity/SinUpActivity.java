package com.bysj.xl.chess.mychess.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsRegSuccessEvent;
import com.bysj.xl.chess.mychess.entity.GetTime.GetNETtime;
import com.bysj.xl.chess.mychess.entity.ReCheck;
import com.bysj.xl.chess.mychess.entity.ReSizeDrawable.ReSizeDrawable;
import com.bysj.xl.chess.mychess.entity.Request;
import com.bysj.xl.chess.mychess.entity.User;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.bysj.xl.chess.mychess.entity.getBitdrawable.getBitdrawble.getbitDrawable;

public class SinUpActivity extends BaseActivity {
    @BindView(R.id.reg_ima_name)
    ImageView reg_ima_name;
    @BindView(R.id.reg_et_name)
    EditText reg_et_name;
    @BindView(R.id.reg_et_pass)
    EditText reg_et_pass;
    @BindView(R.id.reg_ima_pass)
    ImageView reg_ima_pass;
    @BindView(R.id.reg_et_phone)
    EditText reg_et_phone;
    @BindView(R.id.reg_ima_phone)
    ImageView reg_ima_phone;
    @BindView(R.id.reg_et_code)
    EditText reg_et_code;
    @BindView(R.id.reg_ima_code)
    ImageView reg_ima_code;
    @BindView(R.id.reg_bt_sendPhone)
    Button reg_bt_sendPhone;
    @BindView(R.id.reg_ll_signUp)
    LinearLayout reg_ll_signUp;
    @BindView(R.id.reg_bt_signUp)
    Button reg_bt_signUp;
    @BindView(R.id.reg_ll_back)
    LinearLayout reg_ll_back;
    @BindView(R.id.reg_bt_back)
    Button reg_bt_back;

    private EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);//注册回调监听器
    }

    @Override
    protected void initView() {
        initviews();
        intit();
    }

    @SuppressLint("NewApi")
    private void intit() {
        //TODO 初始化控件，并设置长款
        int width = ScreenSizeUtils.getInstance(this).getScreenWidth() / 3 * 2;
        int height = ScreenSizeUtils.getInstance(this).getScreenHeight() / 16;

        reg_et_name.setWidth(width);
        reg_et_name.setHeight(height);
        reg_et_pass.setWidth(width);
        reg_et_pass.setHeight(height);
        reg_et_phone.setWidth(width);
        reg_et_phone.setHeight(height);
        reg_et_code.setWidth(width / 2);
        reg_bt_sendPhone.setWidth(width / 2);

        Drawable drawable3 = ContextCompat.getDrawable(this, R.drawable.ic_message);
        String version_RELEASE = Build.VERSION.SDK;
        if (Integer.parseInt(version_RELEASE) >= 20) {//sdk兼容
            drawable3 = ReSizeDrawable.revector(this, (getbitDrawable((VectorDrawable) drawable3)));
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable3;
            drawable3 = ReSizeDrawable.revector(this, bitmapDrawable.getBitmap());
        }

        reg_ima_code.setImageDrawable(drawable3);


        int temp = ((BitmapDrawable) drawable3).getBitmap().getWidth();
        Log.e(TAG, "intit: " + temp);
        reg_ll_signUp.setLayoutParams(new LinearLayout.LayoutParams((width + temp) / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
        reg_ll_back.setLayoutParams(new LinearLayout.LayoutParams((width + temp) / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initviews() {
        //TODO 加载vector
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_user_name);
//        drawable = ReSizeDrawable.revector(this, (BitmapDrawable) drawable);
        reg_ima_name.setImageDrawable(drawable);

        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.ic_user_password);
//        drawable1 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable1);
        reg_ima_pass.setImageDrawable(drawable1);

        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.ic_phone);
//        drawable2 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable2);
        reg_ima_phone.setImageDrawable(drawable2);


    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_sin_up);
    }

    @OnClick(R.id.reg_bt_sendPhone)
    public void onGetMsgClick() {
        //TODO 获取短信验证码
        String phone = reg_et_phone.getText().toString().trim();
        if (ReCheck.checkPhoneNum(phone)) {
            SMSSDK.getVerificationCode("86", phone);//调用SDK发送短信验证
            reg_bt_sendPhone.setClickable(false);
            new Thread(new Runnable() {
                //TODO 倒计时线程
                @Override
                public void run() {

                    for (int i = 60; i > 0; i--) {
                        if (i < 0) {
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message();
                        msg.what = -1;//倒计时标记
                        msg.arg1 = i;
                        handler.sendMessage(msg);
//                        Log.e(TAG, "run: " + i);
//                        reg_bt_sendPhone.setText("倒计时：" + i);
                    }
                    handler.sendEmptyMessage(-8);//重新获取验证码
                }
            }).start();

        } else {
            myToast.ShowToastShort("请输入正确的手机号");
        }
    }

    @OnClick(R.id.reg_bt_signUp)
    public void onSignUpClick() {
        //TODO 注册
        if (reg_et_name.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入用户昵称");
            return;
        } else if (reg_et_pass.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入用户密码");
            return;
        } else if (reg_et_phone.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入手机号码");
            return;
        }
//        SMSSDK.submitVerificationCode("86", reg_et_phone.getText().toString().trim(), reg_et_code.getText().toString().trim());

        //
        User user = new User();
        user.setUser_name(reg_et_name.getText().toString().trim());
        user.setUsr_passWord(reg_et_pass.getText().toString().trim());
        user.setUsr_phone(reg_et_phone.getText().toString().trim());
        user.setUsr_cretTime(GetNETtime.getInsance().getAllData());
        Request request = new Request();
        request.setData(user);
        request.setUsr_request_type(C.SIGN_UP_MODE);
        String msg1 = new Gson().toJson(request);
        wsInterface.sendMsg(msg1);
    }

    @OnClick(R.id.reg_bt_back)
    public void onBackClick() {
        toActivityWithFinish(LoginActivity.class);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -8) {//重新获取验证码
                reg_bt_sendPhone.setText("获取");
                reg_bt_sendPhone.setClickable(true);
            } else if (msg.what == -1) {
                reg_bt_sendPhone.setText(" " + msg.arg1 + "");
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object object = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //短信注册成功
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //验证码验证成功
                        myToast.ShowToastShort("验证成功");
                        User user = new User();
                        user.setUser_name(reg_et_name.getText().toString().trim());
                        user.setUsr_passWord(reg_et_pass.getText().toString().trim());
                        user.setUsr_phone(reg_et_phone.getText().toString().trim());
                        user.setUsr_cretTime(GetNETtime.getInsance().getAllData());
                        Request request = new Request();
                        request.setData(user);
                        request.setUsr_request_type(C.SIGN_UP_MODE);
                        String msg1 = new Gson().toJson(request);
//                        sendMsg(msg1);
                        wsInterface.sendMsg(msg1);

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        myToast.ShowToastShort("验证码发送成功");
                    }
                }else {
                    Log.e(TAG, "handleMessage: error" + ((Throwable) object).getMessage());
                    String mess = ((Throwable) object).getMessage();
                    String[] msss = mess.split("\\,");
                    String s = msss[0].substring(11,msss[0].indexOf("。"));
                    myToast.ShowToastShort(s);
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void OnRegistSuccessEvent(WsRegSuccessEvent event) {
        Log.e(TAG, "OnRegistSuccessEvent: ");
        myToast.ShowToastShort("注册成功");
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
