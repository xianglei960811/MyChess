package com.bysj.xl.chess.mychess.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.WebSocketSerice;
import com.bysj.xl.chess.mychess.entity.CommonResponse;
import com.bysj.xl.chess.mychess.entity.ReCheck;
import com.bysj.xl.chess.mychess.entity.ReSizeDrawable.ReSizeDrawable;
import com.bysj.xl.chess.mychess.entity.User;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;
import com.bysj.xl.chess.mychess.until.SharedPreferencesUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

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
    @BindView(R.id.log_imabt_weChat)
    ImageButton getLog_imaBt_weChat;

    private volatile Boolean isRember = false;

    @Override
    protected void initView() {
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

    private void initImag() {
        //todo 加载vector图标

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_user);
        drawable = ReSizeDrawable.revector(this, (BitmapDrawable) drawable);
        log_user_ima.setImageDrawable(drawable);

        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.ic_pass);
        drawable1 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable1);
        log_pass_ima.setImageDrawable(drawable1);

        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.sigin);
        drawable2 = ReSizeDrawable.relog(this, (BitmapDrawable) drawable2);
        log_log_ima.setImageDrawable(drawable2);

        Drawable drawable3 = ContextCompat.getDrawable(this, R.drawable.ic_qq);
        drawable3 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable3);
        log_imaBt_qq.setImageDrawable(drawable3);

        Drawable drawable4 = ContextCompat.getDrawable(this, R.drawable.ic_wechat);
        drawable4 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable4);
        getLog_imaBt_weChat.setImageDrawable(drawable4);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean rember = (Boolean) SharedPreferencesUtils.getParam(this, C.IS_REMBER_NAME, C.IS_REMBER);
        log_cb_rember.setChecked(rember);
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
        toActivity(ForgetActivity.class);
    }

    //TODO 登录按钮
    @OnClick(R.id.log_signIn_bt)
    public void onSignInClick() {
        User user = new User();
        if (!checkInput()) return;
        String user_name = login_user_et.getText().toString().trim();
        String pass = log_pass_et.getText().toString().trim();
        if (!ReCheck.checkPassWord(pass)) {
            myToast.ShowToastShort("密码格式错误，请重新输入");
            log_pass_et.setText("");
            return;
        }
        if (ReCheck.checkPhoneNum(user_name)) {
            Log.d(TAG, "onSignInClick: 输入为手机号");
            user.setUsr_phone(Integer.parseInt(user_name));
            user.setUsr_request_type(C.LOGIN_PHONE);
        } else {
            Log.d(TAG, "onSignInClick: 非手机号");
            user.setUsr_AccountId(user_name);
            user.setUsr_request_type(C.LOGIN_ACCONTID);
        }
        user.setUsr_passWord(pass);
        String msg = new Gson().toJson(user);
        Log.d(TAG, "onSignInClick: msg"+msg );
        SharedPreferencesUtils.setParam(this, C.IS_REMBER_NAME, isRember);
        sendMsg(msg);
    }

    //Todo 注册按钮
    @OnClick(R.id.log_signUP_bt)
    public void onSignUpClick() {
    }

    //Todo QQ
    @OnClick(R.id.log_imabt_qq)
    public void onQQclick() {
    }

    //Todo 微信
    @OnClick(R.id.log_imabt_weChat)
    public void onWeChatClick() {
    }

    @Override
    protected void onCommonResponse(CommonResponse<String> resonse) {
        String msg = resonse.getData();
        Log.e(TAG, "onCommonResponse: " + msg);
    }

    @Override
    protected void onErrorResponse(WebSocketErrorEvent errorEvent) {
        Log.e(TAG, "onErrorResponse: " + errorEvent.getMsg());
    }

    @Override
    protected Class<? extends MyService> getWebsocketClass() {
        return WebSocketSerice.class;
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
}
