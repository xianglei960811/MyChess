package com.bysj.xl.chess.mychess.WebSocket.WbManager;

import android.util.Log;

import com.bysj.xl.chess.mychess.Application.App;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsTextEvent;
import com.bysj.xl.chess.mychess.until.ToastUntil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * author:向磊
 * date:2018/12/20
 * Describe:自定义websocket监听
 */
public class WsListener extends WebSocketAdapter {
    private final String TAG = this.getClass().getSimpleName();
    ToastUntil toastUntil =new ToastUntil(App.getContext());

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        super.onConnected(websocket, headers);
        Log.e(TAG, "链接成功: ---------------------------->");
        WsManager.getINSTANCE().setWsStatus(WsStatus.CONNECT_SUCCESS);
        WsManager.getINSTANCE().cancelReconnect();
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
        super.onConnectError(websocket, exception);
        Log.e(TAG, "链接错误: ----------------------->"+exception.getMessage());
        WsManager.getINSTANCE().setWsStatus(WsStatus.CONNECT_FAILE);
        WsManager.getINSTANCE().reConnect();

    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        super.onTextMessage(websocket, text);
        Log.e(TAG, "收到服务器消息:  "+text );
        EventBus.getDefault().postSticky(new WsTextEvent(text));
//        WsManager.getINSTANCE().dispatchMsg(text);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
        Log.e(TAG, "断开链接-------------------------> " );
        WsManager.getINSTANCE().setWsStatus(WsStatus.CONNECT_FAILE);
        WsManager.getINSTANCE().reConnect();//断开链接后尝试重连
    }


}
