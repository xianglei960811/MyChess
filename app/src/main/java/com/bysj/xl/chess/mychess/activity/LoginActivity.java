package com.bysj.xl.chess.mychess.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.WebSocketSerice;
import com.bysj.xl.chess.mychess.entity.CommonResponse;


import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_message)
    TextView login_message;
    @BindView(R.id.login_close)
    Button login_close;

    @Override
    protected void initView() {

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

    @OnClick(R.id.login_close)
    public void onCloseClick() {
        senMsg("客户端1");
    }
}
