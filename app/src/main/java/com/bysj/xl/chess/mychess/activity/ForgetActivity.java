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
import com.bysj.xl.chess.mychess.entity.ReCheck;
import com.bysj.xl.chess.mychess.entity.Request;
import com.bysj.xl.chess.mychess.entity.TencentUser;
import com.bysj.xl.chess.mychess.until.ToastUntil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetActivity extends BaseActivity {
    @BindView(R.id.for_et_code)
    EditText forEtCode;
    @BindView(R.id.for_et_phone)
    EditText forEtPhone;
    @BindView(R.id.for_bt_getCode)
    Button forBtGetcode;
    @BindView(R.id.for_bt_back)
    Button forBtBack;
    @BindView(R.id.for_bt_enSure)
    Button forBtEnsure;

    private EventHandler eventHandler;
    private volatile String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_forget);
    }

    @OnClick(R.id.for_bt_back)
    public void onBackClick() {
        toActivityWithFinish(LoginActivity.class);
    }

    @OnClick(R.id.for_bt_enSure)
    public void onEnsureClick() {
//        toActivity(ModifyPasswordActivity.class);
        if (forEtPhone.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入手机号！");
            return;
        }
        if (forEtCode.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入短信验证码！");
            return;
        }
        SMSSDK.submitVerificationCode("86", forEtPhone.getText().toString().trim(), forEtCode.getText().toString().trim());
    }

    @OnClick(R.id.for_bt_getCode)
    public void onGetcodeClick() {
        // TODO: 2019/1/8 获取短信验证码
        phone = forEtPhone.getText().toString().trim();
        if (ReCheck.checkPhoneNum(phone)) {
            Request request = new Request();
            TencentUser user = new TencentUser();
            request.setUsr_request_type(C.FORGET_PHONE);
            user.setUsr_phone(phone);
            request.setData(user);
            request.setMsg("check phone is exist");
            wsInterface.sendMsg(new Gson().toJson(request));
        } else {
            myToast.ShowToastShort("请输入正确的手机号码!");
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -8) {//重新获取验证码
                forBtGetcode.setText("获取");
                forBtGetcode.setClickable(true);
                return;
            } else if (msg.what == -1) {
                forBtGetcode.setText(" " + msg.arg1 + "");
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
                        Intent intent =new Intent(ForgetActivity.this,ModifyPasswordActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("phone",phone);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        App.getInstance().removeActivity(ForgetActivity.this);
//                        toActivityWithFinish(ModifyPasswordActivity.class);
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
//                    mess.substring(11,mess.indexOf("httpStatus"));
                        String[] msss = mess.split("\\,");
//                    String s = msss[0].substring(11, msss[0].indexOf("。"));
                        String s = msss[0];
//                    myToast.ShowToastShort(s);
//                    ((Throwable) object).printStackTrace();
                        new ToastUntil(ForgetActivity.this).ShowToastShort(s);
                        return;
                    }
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void ForgetPhoneSuccessEvent(WsForgetPhoneSuccessEvent<TencentUser> event) {
        // TODO: 2019/1/8 验证该手机号用户存在事件,则开始获取验证码
        SMSSDK.getVerificationCode("86", phone);
        Log.d(TAG, "ForgetPhoneSuccessEvent: 开始验证获取验证码---------------------》");
        forBtGetcode.setClickable(false);

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
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
