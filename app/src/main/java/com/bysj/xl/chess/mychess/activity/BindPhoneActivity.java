package com.bysj.xl.chess.mychess.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.bysj.xl.chess.mychess.Application.App;
import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsForgetPhoneSuccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsUserPhoneSuccessEvent;
import com.bysj.xl.chess.mychess.entity.ReCheck;
import com.bysj.xl.chess.mychess.entity.Request;
import com.bysj.xl.chess.mychess.entity.TencentUser;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.bind_et_phone)
    EditText bindEtPhone;
    @BindView(R.id.bind_phone_code)
    EditText bindEtPhoneCode;
    @BindView(R.id.bind_bt_ensure)
    Button bindBtEnsure;
    @BindView(R.id.bind_bt_back)
    Button bindBtBack;
    @BindView(R.id.bind_bt_getCode)
    Button bindBtGetcode;

    private volatile String phone;
    private EventHandler eventHandler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
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
        SMSSDK.registerEventHandler(eventHandler);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_bind_phone);
    }

    @OnClick(R.id.bind_bt_getCode)
    public void onGetCode() {
        phone = bindEtPhone.getText().toString().trim();
        if (phone == null || phone.isEmpty()) {
            myToast.showToastMain("请输入手机号码");
            return;
        }
        if (ReCheck.checkPhoneNum(phone)) {
            Request request = new Request();
            TencentUser user = new TencentUser();
            user.setUsr_phone(phone);
            request.setData(user);
            request.setUsr_request_type(C.FORGET_PHONE);
            request.setMsg("check phone is exist");
//            wsInterface.sendMsg(new Gson().toJson(request));   从服务器验证手机号
//以下为测试，正式使用服务器时许删除
            SMSSDK.getVerificationCode("86", phone);//调用SDK发送短信验证
            bindBtGetcode.setClickable(false);
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
                    }
                    handler.sendEmptyMessage(-8);//重新获取验证码
                }
            }).start();
        } else {
            myToast.showToastMain("请输入正确的手机号码");
        }
    }

    @OnClick(R.id.bind_bt_ensure)
    public void onEnsureClick() {
        if (bindEtPhone.getText().toString().trim() == null || bindEtPhone.getText().toString().trim().isEmpty()) {
            myToast.showToastMain("请输入手机号码");
            return;
        }
        if (bindEtPhoneCode.getText().toString().trim() == null || bindEtPhoneCode.getText().toString().trim().isEmpty()) {
            myToast.showToastMain("请输入验证码");
            return;
        }
//        SMSSDK.submitVerificationCode("86", bindEtPhone.getText().toString().trim(), bindEtPhoneCode.getText().toString().trim());//短信验证
        Intent intent = getIntent();


        Request request = new Request();
        TencentUser user = new TencentUser();
        String name = intent.getStringExtra("name");
        Log.e(TAG, "onEnsureClick: ------------------------>" + name);
        user.setUser_name(name);
        user.setUsr_phone(bindEtPhone.getText().toString().trim());
        request.setData(user);
        request.setUsr_request_type(C.USE_PHONE);
        request.setMsg("Moodify the user's phone");
        wsInterface.sendMsg(new Gson().toJson(request));

    }

    @OnClick(R.id.bind_bt_back)
    public void onBackClick() {
        toActivityWithFinish(UsersActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void checkPhoneSuccess(WsForgetPhoneSuccessEvent<TencentUser> event) {
        //验证手机号码更新是存在
        SMSSDK.getVerificationCode("86", phone);//调用SDK发送短信验证
        bindBtGetcode.setClickable(false);
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
                }
                handler.sendEmptyMessage(-8);//重新获取验证码
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -8) {//重新获取验证码
                bindBtGetcode.setText("获取");
                bindBtGetcode.setClickable(true);
                return;
            } else if (msg.what == -1) {
                bindBtGetcode.setText(" " + msg.arg1 + "");
                return;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object object = msg.obj;
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //短信注册成功
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //验证码验证成功
                        myToast.ShowToastShort("验证成功");
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", bindEtPhone.getText().toString().trim());
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                        App.getInstance().removeActivity(BindPhoneActivity.this);
                        return;
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        myToast.ShowToastShort("验证码发送成功");
                        return;
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家
                        return;
                    } else {
                        Log.e(TAG, "handleMessage: error" + ((Throwable) object).getMessage());
                        String mess = ((Throwable) object).getMessage();
                        String[] msss = mess.split("\\,");
                        String s = msss[0];
                        myToast.ShowToastShort(s);
                        return;
                    }
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void UserPhoneSuccessEvent(WsUserPhoneSuccessEvent event){
        myToast.showToastMain("手机更换绑定成功");
        Bundle bundle = new Bundle();
        bundle.putString("phone", bindEtPhone.getText().toString().trim());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        App.getInstance().removeActivity(BindPhoneActivity.this);
    }
}
