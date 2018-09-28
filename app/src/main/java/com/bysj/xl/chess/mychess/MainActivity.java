package com.bysj.xl.chess.mychess;

import android.os.Bundle;
import android.widget.ImageView;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;

import butterknife.BindView;

public class MainActivity extends BaseActivity{

    @BindView(R.id.main_image)
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
//        Drawable drawable  = ContextCompat.getDrawable(this,R.drawable.chessbroad);
//        drawable = ReSizeDrawable.reSize(this, (BitmapDrawable) drawable);
//        imageView.setImageDrawable(drawable);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected Class<? extends MyService> getWebsocketClass() {
        return null;
    }


    @Override
    protected void onErrorResponse(WebSocketErrorEvent errorEvent) {

    }
}
