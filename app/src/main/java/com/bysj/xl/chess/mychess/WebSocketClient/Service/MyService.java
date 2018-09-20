package com.bysj.xl.chess.mychess.WebSocketClient.Service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.WebSocketClient.IWebSocket;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.DisconnectedEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketConnectedEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketConnectionErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.neovisionaries.ws.client.ProxySettings;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * USER:向磊
 * DATE:2018/9/17
 * Describe:服务器通信
 */
public abstract class MyService extends Service implements IWebSocket {
    private static final String TAG = "MyService";
    private static final int TIME_OUT = 1000;

    private WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(TIME_OUT);

    private MyService.WebSocketThread webSocketThread;
    private WebSocket webSocket;

    private ServiceBinder MyBinder = new ServiceBinder();

    private boolean isStop = false;
    /**
     * 是否已连接
     * 0-未连接
     * 1-正在连接
     * 2-已连接
     */
    private volatile int connectStatus = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (MyBinder == null) {
            MyBinder = new ServiceBinder();
        }
        Log.d(TAG, "onBind: ");
        return MyBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        ProxySettings settings = factory.getProxySettings();
        settings.addHeader("content", "text/json");

        connectStatus = 0;
        webSocketThread = new WebSocketThread();
        webSocketThread.start();
    }

    public class ServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

//    /**
//     * 获取服务器地址
//     *
//     * @return
//     */
//    protected abstract String getConnectURl();

    /**
     * 分发响应数据
     *
     * @param responseMsg
     */
    protected abstract void dispatchResponse(String responseMsg);

    /**
     * socket连接线程
     * 连接成功发送 WebSocketConnectedEvent 事件，
     * 请求成功发送 CommonResponse 事件，
     * 请求失败发送 webSocketErrorEvent 事件。
     */
    public class WebSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            Log.i(TAG, "WebSocketThread->run: ");
            setupWebSocket();
        }
    }

    private void setupWebSocket() {
        if (connectStatus != 0) return;
        connectStatus = 1;
        try {
            webSocket = factory.createSocket(getConnectUrl());
            webSocket.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String text) throws Exception {
                    super.onTextMessage(websocket, text);
                    if (debug()) {
                        Log.i(TAG, String.format("onTextMessage->%s", text));

                    }
                    dispatchResponse(text);
                }

                @Override
                public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
                    super.onTextMessageError(websocket, cause, data);
                    Log.e(TAG, "onTextMessageError: ", cause);
                    EventBus.getDefault().postSticky(new WebSocketErrorEvent("onTextMessageError():") + cause.toString());
                }

                @Override
                public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                    super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
                    EventBus.getDefault().postSticky(new DisconnectedEvent());
                    Log.e(TAG, "onDisconnected: ");
                    connectStatus = 0;
                    if (!isStop) {
                        //断开后重连
                        setupWebSocket();
                    }
                }

                @Override
                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                    super.onConnected(websocket, headers);
                    Log.i(TAG, "onConnected: ");
                    connectStatus = 2;
                    EventBus.getDefault().postSticky(new WebSocketConnectedEvent());
                }

                @Override
                public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                    super.onError(websocket, cause);
                    Log.e(TAG, "onError: ", cause);
                    EventBus.getDefault().postSticky(new WebSocketConnectionErrorEvent(cause.getMessage()));
                }
            });
                webSocket.connect();
        } catch (IOException e) {
            connectStatus = 0;
            Log.i(TAG, String.format("IOException()->%s", e.getMessage()));
            Log.e(TAG, "IOException()", e);
            EventBus.getDefault().postSticky(new WebSocketConnectionErrorEvent("IOException:" + e.getMessage()));
        } catch (WebSocketException e) {
            connectStatus = 0;
            Log.e(TAG, "setupWebSocket: ----->WebSocketException"+e.getMessage() );
            EventBus.getDefault().postSticky(new WebSocketConnectionErrorEvent("WebSocketException"+e.getMessage()));
        }
    }


    @Override
    public void sendMsg(String msg) {
     if (msg.isEmpty()) return;
     if (debug()){
         Log.i(TAG, String.format("sendText()->%s", msg));
     }
        if (webSocket != null && connectStatus == 2) {
            webSocket.sendText(msg);
        }
}

    @Override
    public int getConnectStatus() {
        return connectStatus;
    }

    @Override
    public void reConnect() {
        Log.d(TAG, "reConnect: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "reConnect->run: ");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "reConnect->run: "+e.getMessage() );
                }
                if (webSocketThread!=null&&!webSocketThread.isAlive()){
                    connectStatus=0;
                    webSocketThread = new WebSocketThread();
                    webSocketThread.start();
                }else {
                    Log.i(TAG, "reconnect()->start failed: webSocketThread==null || webSocketThread.isAlive()");
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop: ");
        webSocket.disconnect();
        isStop =true;

    }


    public boolean debug() {
        try {
            ApplicationInfo info = getApplication().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        isStop = true;
        webSocket.disconnect();
        webSocket.flush();
        webSocket = null;
        connectStatus = 0;
    }

}
