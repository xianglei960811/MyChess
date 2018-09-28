package com.bysj.xl.chess.mychess.activity;

import android.os.Bundle;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.WebSocketSerice;

public class ForgetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setContentView() {

    }

    @Override
    protected Class<? extends MyService> getWebsocketClass() {
        return WebSocketSerice.class;
    }


    @Override
    protected void onErrorResponse(WebSocketErrorEvent errorEvent) {

    }
}
