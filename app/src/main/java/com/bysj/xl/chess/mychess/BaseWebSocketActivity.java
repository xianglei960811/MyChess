package com.bysj.xl.chess.mychess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketConnectedEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketConnectionErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.WebSocketClient.IWebSocket;
import com.bysj.xl.chess.mychess.entity.CommonResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author:向磊
 * date:2018/9/17
 * Describe:
 */
public abstract class BaseWebSocketActivity extends BaseActivity {
    protected String TAG = this.getClass().getSimpleName();
    private IWebSocket mWebSocketService;
    protected String networkErrorTips;

    /**
     * 连接时机：</br>
     * 0 - 刚进入界面时，如果 WebSocket 还未连接，会继续连接，或者由于某些原因 WebSocket 断开，会自动重连，从而会触发连接成功/失败事件；</br>
     * 1 - onResume() 方法回调时判断 WebSocket 是否连接，如果未连接，则进行连接，从而触发连接成功/失败事件；</br>
     * 2 - sendText() 方法会判断 WebSocket 是否已经连接，如果未连接，则进行连接，从而触发连接成功/失败事件，此时连接成功后应继续调用 sendText() 方法发送数据。</br>
     * <p>
     * 另外，当 connectType != 0 时，每次使用完之后应该设置为 0。因为 0 的状态是无法预知的，随时可能调用。
     */
    private int connectType = 0;
    private String sendText;
    private boolean isConnect = false;
    private boolean networkReceiverIsRegister = false;
    private int connectTime = 0;
    protected ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mWebSocketService =  ((MyService.ServiceBinder) iBinder).getService();
            if (mWebSocketService.getConnectStatus() == 2) {
                Log.d(TAG, "onServiceConnected: ");
                onServiceBindSuccess();
            } else {
                if (mWebSocketService.getConnectStatus() == 0) {
                    mWebSocketService.reConnect();
                    Log.i(TAG, "onServiceConnected:reconnect ");
                }
                showProgressDialog();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mWebSocketService = null;
            if (connectTime <= C.RECONNECT_TIME) {
                bindWebSocketService();
            }
        }
    };

    @Override
    protected void initView() {
        Log.e(TAG, "initView: ");
        initBind();
    }

    /**
     * 绑定服务，
     * 进入界面时绑定服务，
     * 失败则重新绑定，直到超过次数
     */
    private void bindWebSocketService() {
        Log.e(TAG, "bindWebSocketService: " );
        Intent intent = new Intent(this, getWebsocketClass());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        connectTime++;
        Log.i(TAG, "bindWebSocketService: success");
    }

    /**
     * 服务器绑定成功的回调方法
     */
    private void onServiceBindSuccess() {
    }


    @Override
    protected void setContentView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //todo 从后台返回时，判断服务是否已断开，断开则重连
        if (mWebSocketService != null && mWebSocketService.getConnectStatus() != 2) {
            Log.e(TAG, "onResume: ->未连接");
            showProgressDialog();
            if (mWebSocketService.getConnectStatus() == 0) {
                Log.i(TAG, "onResume: ---->尝试重连");
                mWebSocketService.reConnect();
            } else {
                Log.i(TAG, "onResume: --->正在连接");
            }
            connectType = 1;
        }
    }

    /**
     * 初始化bind
     */
    private void initBind() {
        networkErrorTips = "网络错误";
        EventBus.getDefault().register(this);
        bindWebSocketService();
    }

    protected abstract void onCommonResponse(CommonResponse<String> resonse);

    protected abstract void onErrorResponse(WebSocketErrorEvent errorEvent);

    protected void sendText(String msg) {
//        if (mWebSocketService.getConnectStatus() == 2) {
        if (true){
            Log.i(TAG, "sendText: 已连接，发送数据");
            mWebSocketService.sendMsg(msg);
        } else {
            Log.i(TAG, "sendText: 未连接，先连接再发送数据");
            connectType = 2;
            sendText = msg;
            if (mWebSocketService.getConnectStatus() == 0) {
                Log.i(TAG, "sendText: 建立连接");
                mWebSocketService.reConnect();
            }
        }
    }

    protected void onConnerctedFailed() {
        Log.i(TAG, "onConnerctedFailed: ");
    }

    protected IWebSocket getmWebSocketService() {
        return mWebSocketService;
    }

    protected abstract Class<? extends MyService> getWebsocketClass();

    /**
     * 返回的
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(CommonResponse<String> event) {
        onCommonResponse(event);
    }

    /**
     * 错误信息
     * @param event 错误信息事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(WebSocketErrorEvent event) {
        onErrorResponse(event);
    }

    /**
     * 连接成功
     * @param event 成功事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(WebSocketConnectedEvent event) {
        isConnect =true;
        if (connectType==2&&!sendText.isEmpty()){
            Log.i(TAG, "onEventMainThread: -------------->senText");
            sendText(sendText);
        }else if (connectType==0){
            Log.i(TAG, "onEventMainThread: ------------------->onServiceBindSuccess");
            onServiceBindSuccess();
        }
        connectType = 0;
    }

    /**
     * 连接失败
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(WebSocketConnectionErrorEvent event) {
        Log.e(TAG, "WebSocketConnectionErrorEvent: ---------->"+event.toString());
        myToast.ShowToastShort(networkErrorTips);
        connectType=0;
        onConnerctedFailed();
    }
    private void showProgressDialog() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: --------------------------------------->");
        unbindService(connection);
        EventBus.getDefault().unregister(this);
    }
}
