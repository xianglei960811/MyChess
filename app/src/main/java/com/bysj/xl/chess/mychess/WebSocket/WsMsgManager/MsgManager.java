package com.bysj.xl.chess.mychess.WebSocket.WsMsgManager;

import android.util.Log;

import com.bysj.xl.chess.mychess.WebSocket.WbManager.WsManager;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WebSocketConnectionErrorEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsTextEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author:向磊
 * date:2018/12/21
 * Describe:管理ws的接收到的消息，发送的消息，错误的消息
 */
public abstract class MsgManager {
    protected MsgManager() {
        if (!EventBus.getDefault().isRegistered(MsgManager.this)) {
            EventBus.getDefault().register(MsgManager.this);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void ReeiveTextEvent(WsTextEvent event) {
        Log.e("MSgManager", "ReeiveTextEvent: " + event.getMsg());
        dispatchResponse(event.getMsg());
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void WsConnError(WebSocketConnectionErrorEvent errorEvent) {
        Log.e("MsgManager", "WsConnError: " + errorEvent.toString());
        WsManager.getINSTANCE().reConnect();//链接发生错误时，尝试重新链接
        EventBus.getDefault().removeStickyEvent(errorEvent);
    }

    //分发数据接口
    protected abstract void dispatchResponse(String msg);
}
