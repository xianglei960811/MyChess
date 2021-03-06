package com.bysj.xl.chess.mychess.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsLogSuccessEvent;
import com.bysj.xl.chess.mychess.entity.ReCheck;
import com.bysj.xl.chess.mychess.entity.ReSizeDrawable.ReSizeDrawable;
import com.bysj.xl.chess.mychess.entity.Request;
import com.bysj.xl.chess.mychess.entity.TencentUser;
import com.bysj.xl.chess.mychess.entity.User;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;
import com.bysj.xl.chess.mychess.until.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.bysj.xl.chess.mychess.entity.getBitdrawable.getBitdrawble.getbitDrawable;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_user_et)
    TextView login_user_et;
    @BindView(R.id.log_user_ima)
    ImageView log_user_ima;
    @BindView(R.id.log_user_ll)
    LinearLayout log_user_ll;
    @BindView(R.id.log_pass_ima)
    ImageView log_pass_ima;
    @BindView(R.id.login_pass_et)
    EditText log_pass_et;
    @BindView(R.id.log_log_ima)
    ImageView log_log_ima;
    @BindView(R.id.log_signIn_bt)
    Button log_sigIn_bt;
    @BindView(R.id.log_signUP_bt)
    Button log_signUP_bt;
    @BindView(R.id.log_tv_forget)
    TextView log_tv_forget;
    @BindView(R.id.log_cb_rember)
    CheckBox log_cb_rember;
    @BindView(R.id.log_rember_ll)
    LinearLayout log_rember_ll;
    @BindView(R.id.log_imabt_qq)
    ImageButton log_imaBt_qq;

    private volatile Boolean isRember = false;
    private volatile String user_name;
    private volatile String pass;
    private Request request;
    private Tencent mTencent; //Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI


    @Override
    protected void initView() {
        mTencent = Tencent.createInstance("1107791795", LoginActivity.this);
        request = new Request();
        initImag();
        initviews();
        log_pass_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        log_pass_et.setSelection(log_pass_et.getText().length());
    }

    //初始化各控件
    private void initviews() {
        int width = ScreenSizeUtils.getInstance(this).getScreenWidth() / 2;
        int height = ScreenSizeUtils.getInstance(this).getScreenHeight() / 16;
//        log_user_ll.setLayoutParams(new LinearLayout.LayoutParams(width,height));
        //动态获取宽度
        int space = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        log_user_ll.measure(space, space);
        int ll_width = log_user_ll.getMeasuredWidth();
        log_rember_ll.setLayoutParams(new LinearLayout.LayoutParams(ll_width, ViewGroup.LayoutParams.WRAP_CONTENT));

        login_user_et.setWidth(width);
        login_user_et.setHeight(height);
        log_pass_et.setWidth(width);
        login_user_et.setHeight(height);
        log_signUP_bt.setWidth(width / 3 * 2);
        log_signUP_bt.setHeight(height);
        log_sigIn_bt.setWidth(width / 3 * 2);
        log_sigIn_bt.setHeight(height);
    }

    @SuppressLint("NewApi")
    private void initImag() {
        //todo 加载vector图标

        String version_RELEASE = Build.VERSION.SDK;
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_user);
        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.ic_pass);
        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.sigin);
        Drawable drawable3 = ContextCompat.getDrawable(this, R.drawable.ic_qq);
        if (Integer.parseInt(version_RELEASE) >= 20) {
            drawable = ReSizeDrawable.revector(this, getbitDrawable((VectorDrawable) drawable));

            drawable1 = ReSizeDrawable.revector(this, (getbitDrawable((VectorDrawable) drawable1)));

            drawable2 = ReSizeDrawable.relog(this, (BitmapDrawable) drawable2);

            drawable3 = ReSizeDrawable.revector(this, (getbitDrawable((VectorDrawable) drawable3)));
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            drawable = ReSizeDrawable.revector(this, bitmapDrawable.getBitmap());

            BitmapDrawable bitmapDrawabl1 = (BitmapDrawable) drawable1;
            drawable1 = ReSizeDrawable.revector(this, bitmapDrawabl1.getBitmap());

            BitmapDrawable bitmapDrawabl2 = (BitmapDrawable) drawable2;
            drawable2 = ReSizeDrawable.revector(this, bitmapDrawabl2.getBitmap());

            BitmapDrawable bitmapDrawable3 = (BitmapDrawable) drawable3;
            drawable3 = ReSizeDrawable.revector(this, bitmapDrawable3.getBitmap());
        }
        log_user_ima.setImageDrawable(drawable);
        log_pass_ima.setImageDrawable(drawable1);
        log_log_ima.setImageDrawable(drawable2);
        log_imaBt_qq.setImageDrawable(drawable3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Boolean rember = (Boolean) SharedPreferencesUtils.getParam(this, C.IS_REMBER_NAME, C.IS_REMBER);
        log_cb_rember.setChecked(rember);
        if (rember) {
            login_user_et.setText((String) SharedPreferencesUtils.getParam(this, C.USER_NAME_NAME, C.USER_NAME));
            log_pass_et.setText((String) SharedPreferencesUtils.getParam(this, C.PASS_WORD_NAME, C.PASS_WORD));
        }

//        sendText("客户端1");
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    // TODO: rember按钮
    @OnCheckedChanged(R.id.log_cb_rember)
    public void onRemberClick(CompoundButton buttonview, boolean isChecked) {
        if (isChecked) {
            isRember = true;
        } else {
            isRember = false;
        }
    }

    //TODO 忘记密码按钮
    @OnClick(R.id.log_tv_forget)
    public void onForgetClick() {
        toActivityWithFinish(ForgetActivity.class);
    }

    //TODO 登录按钮
    @OnClick(R.id.log_signIn_bt)
    public void onSignInClick() {
        User user = new User();
        if (!checkInput()) return;
        user_name = login_user_et.getText().toString().trim();
        pass = log_pass_et.getText().toString().trim();
        if (!ReCheck.checkPassWord(pass)) {
            myToast.ShowToastShort("密码格式错误，请重新输入");
            log_pass_et.setText("");
            return;
        }
        user.setUsr_passWord(pass);
        if (ReCheck.checkPhoneNum(user_name)) {
            Log.d(TAG, "onSignInClick: 输入为手机号");
            user.setUsr_phone(user_name);
            request.setUsr_request_type(C.LOGIN_PHONE);
        } else {
            Log.d(TAG, "onSignInClick: 非手机号");
            user.setUsr_AccountId(user_name);
            request.setUsr_request_type(C.LOGIN_ACCONTID);
        }
        request.setData(user);
        request.setMsg("Loging ");
        String msg = new Gson().toJson(request);
        Log.d(TAG, "onSignInClick: msg" + msg);
        SharedPreferencesUtils.setParam(this, C.IS_REMBER_NAME, isRember);
//        sendMsg(msg);
        wsInterface.sendMsg(msg);
    }

    //Todo 注册按钮
    @OnClick(R.id.log_signUP_bt)
    public void onSignUpClick() {
        toActivityWithFinish(SinUpActivity.class);
    }

    //Todo QQ
    @OnClick(R.id.log_imabt_qq)
    public void onQQclick() {
        mTencent.login(this, "all", new QQcallBack());
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void logSuceessEvent(WsLogSuccessEvent<TencentUser> event) {
        myToast.ShowToastShort("登录成功");
        Log.e(TAG, "logSuceessEvent: " + event.toString());
        SharedPreferencesUtils.setParam(this, C.IS_REMBER_NAME, isRember);
        if (isRember) {
            Log.d(TAG, "logAccount:ssssssss " + event.getData().getUsr_phone() + ":" + event.getData().getUsr_passWord());
            SharedPreferencesUtils.setParam(this, C.PASS_WORD_NAME, event.getData().getUsr_passWord());
        }
        String name = event.getData().getUser_name();
        String QQ = event.getData().getQQ();
//        String headIma = event.getData().getUser_headIma();
        String phone = event.getData().getUsr_phone();
        String grade = event.getData().getUser_grade();
        String sex = event.getData().getUsr_Sex();
        int age = event.getData().getUsr_age();
        String cretTime = event.getData().getUsr_cretTime();
        if (name != null) {
            SharedPreferencesUtils.setParam(this, C.USER_NAME_NAME, event.getData().getUser_name());
        }
        if (QQ != null) {
            SharedPreferencesUtils.setParam(this, C.USER_QQ_NAME, event.getData().getQQ());
        }
//        if (headIma != null) {
//            SharedPreferencesUtils.setParam(this, C.USER_HEAD_NAME, event.getData().getUser_headIma());
//        }
        if (phone != null) {
            SharedPreferencesUtils.setParam(this, C.USER_PHONE_NAME, event.getData().getUsr_phone());
        }
        if (grade != null) {
            SharedPreferencesUtils.setParam(this, C.USER_GRADE_NAME, event.getData().getUser_grade());
        }
        if (sex != null) {
            SharedPreferencesUtils.setParam(this, C.USER_SEX_NAME, event.getData().getUsr_Sex());
        }
            SharedPreferencesUtils.setParam(this, C.USER_AGE_NAME, event.getData().getUsr_age());
        if (cretTime != null) {
            SharedPreferencesUtils.setParam(this, C.USER_CREAT_TIME_NAME, event.getData().getUsr_cretTime());
        }
        EventBus.getDefault().removeStickyEvent(event);

        toActivityWithFinish(MainGameActivity.class);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new QQcallBack());
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new QQcallBack());
            }
        }
    }

    /**
     * 判断是否输入为空
     *
     * @return
     */
    private Boolean checkInput() {
        if (login_user_et.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入用户名");
            return false;
        } else if (log_pass_et.getText().toString().trim().isEmpty()) {
            myToast.ShowToastShort("请输入密码");
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myToast.stopToast();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * qq登录的回调接口
     */
    private class QQcallBack implements IUiListener {

        @Override
        public void onComplete(Object response) {
//            myToast.ShowToastShort("登录成功");
            try {
                String openId = ((JSONObject) response).getString("openid");
                TencentUser user = new TencentUser();
                user.setQQopenID(openId);
                request.setUsr_request_type(C.LOGIN_QQ);
                request.setData(user);
                request.setMsg("QQ Loging");
//                sendMsg(new Gson().toJson(request));
                wsInterface.sendMsg(new Gson().toJson(request));
                Log.e(TAG, "onComplete: openid:" + openId);
            } catch (JSONException e) {
                Log.e(TAG, "onComplete: getOpenID faile::" + e.getMessage());
//                e.printStackTrace();
            }
//            QQToken qqToken = mTencent.getQQToken();
//            UserInfo userInfo = new UserInfo(LoginActivity.this, qqToken);
//            userInfo.getUserInfo(new IUiListener() {
//                @Override
//                public void onComplete(Object o) {
//                    Log.e(TAG, "QQonComplete: qq信息：" + o.toString());
//                }
//
//                @Override
//                public void onError(UiError uiError) {
//                    Log.e(TAG, "qqonError: UserInfo" + uiError.errorMessage);
//                }
//
//                @Override
//                public void onCancel() {
//                    Log.e(TAG, "qqUserInfo: ");
//                }
//            });
        }

        @Override
        public void onError(UiError uiError) {
            Log.e(TAG, "QQonError: " + uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "QQonCancel: ");
        }
    }
}
