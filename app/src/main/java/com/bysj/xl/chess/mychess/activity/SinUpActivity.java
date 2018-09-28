package com.bysj.xl.chess.mychess.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.WebSocketSerice;
import com.bysj.xl.chess.mychess.entity.ReSizeDrawable.ReSizeDrawable;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;

import butterknife.BindView;

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

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        initviews();
        intit();
    }

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
        drawable3 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable3);
        reg_ima_code.setImageDrawable(drawable3);

        int temp = ((BitmapDrawable) drawable3).getBitmap().getWidth();
        Log.e(TAG, "intit: " + temp);
        reg_ll_signUp.setLayoutParams(new LinearLayout.LayoutParams((width + temp) / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
        reg_ll_back.setLayoutParams(new LinearLayout.LayoutParams((width + temp) / 2, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void initviews() {
        //TODO 加载vector
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_user_name);
        drawable = ReSizeDrawable.revector(this, (BitmapDrawable) drawable);
        reg_ima_name.setImageDrawable(drawable);

        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.ic_user_password);
        drawable1 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable1);
        reg_ima_pass.setImageDrawable(drawable1);

        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.ic_phone);
        drawable2 = ReSizeDrawable.revector(this, (BitmapDrawable) drawable2);
        reg_ima_phone.setImageDrawable(drawable2);


    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_sin_up);
    }

    @Override
    protected Class<? extends MyService> getWebsocketClass() {
        return WebSocketSerice.class;
    }

    @Override
    protected void onErrorResponse(WebSocketErrorEvent errorEvent) {

    }
}
