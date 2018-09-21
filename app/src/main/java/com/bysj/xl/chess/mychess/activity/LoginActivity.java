package com.bysj.xl.chess.mychess.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.WebSocketSerice;
import com.bysj.xl.chess.mychess.entity.CommonResponse;
import com.bysj.xl.chess.mychess.entity.ReSizeDrawable.ReSizeDrawable;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;


import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_user_et)
    TextView login_user_et;
    @BindView(R.id.log_user_ima)
    ImageView log_user_ima;
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
    @Override
    protected void initView() {
        initImag();
        int width = ScreenSizeUtils.getInstance(this).getScreenWidth() / 2;
        int height = ScreenSizeUtils.getInstance(this).getScreenHeight() / 16;
//        log_user_ll.setLayoutParams(new LinearLayout.LayoutParams(width,height));
        login_user_et.setWidth(width);
        login_user_et.setHeight(height);
        log_pass_et.setWidth(width);
        login_user_et.setHeight(height);
        log_signUP_bt.setWidth(width/3*2);
        log_signUP_bt.setHeight(height);
        log_sigIn_bt.setWidth(width/3*2);
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


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        sendText("客户端1");
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
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

}
