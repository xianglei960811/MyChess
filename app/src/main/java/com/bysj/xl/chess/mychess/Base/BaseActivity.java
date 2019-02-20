package com.bysj.xl.chess.mychess.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.bysj.xl.chess.mychess.Application.App;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocket.WbManager.WsInterface;
import com.bysj.xl.chess.mychess.WebSocket.WsMsgManager.RsponseMsgHandler;
import com.bysj.xl.chess.mychess.until.ToastUntil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();
    protected ToastUntil myToast;
    private long firstTime = 0;//记录用户首次点击返回键的事件
    protected WsInterface wsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.ac_slide_right_in, R.anim.ac_slide_left_out);
        myToast = new ToastUntil(this);
        setContentView();
        ButterKnife.bind(this);
        initView();
//        initBind();
        App.getInstance().addActivity(this);
        if (wsInterface == null) {
            wsInterface = new RsponseMsgHandler();
        }
    }

    protected abstract void initView();

    protected abstract void setContentView();


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void toActivityWithFinish(Class<?> toclass) {
        startActivity(new Intent(this, toclass));
        App.getInstance().removeActivity(this);
    }

    public void toActivity(Class<?> toclass) {
        startActivity(new Intent(this, toclass));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            App.getInstance().removeActivity(this);
            Long sceondTime = System.currentTimeMillis();
            if (sceondTime - firstTime > 2000) {
                myToast.ShowToastShort("再按一次推出程序");
                firstTime = sceondTime;
                return true;
            } else {
                System.exit(0);
            }

        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public void finish() {
//        overridePendingTransition(R.anim.ac_slide_left_in, R.anim.ac_slide_right_out);
//        super.finish();
//        Log.e(TAG, "finish: ");
//        App.getInstance().exitApp();
//    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy:--------------------.> ");
        myToast.stopToast();
        wsInterface = null;
        App.getInstance().removeActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
