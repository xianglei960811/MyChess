package com.bysj.xl.chess.mychess.WebSocket.WbManager;

import android.os.Handler;
import android.util.Log;

import com.bysj.xl.chess.mychess.Application.App;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WebSocketConnectionErrorEvent;
import com.bysj.xl.chess.mychess.until.CheckNetUntil;
import com.bysj.xl.chess.mychess.until.ToastUntil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\  =  /O
 * ____/`---'\____
 * .'  \\|     |//  `.
 * /  \\|||  :  |||//  \
 * /  _||||| -:- |||||-  \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |   |
 * \  .-\__  `-`  ___/-. /
 * ___`. .'  /--.--\  `. . __
 * ."" '<  `.___\_<|>_/___.'  >'"".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 佛祖保佑        永无BUG
 */

/**
 * author:向磊
 * date:2018/12/20
 * Describe:websocket管理类
 */
public class WsManager {
    private static WsManager INSTANCE = null;
    private static final String TAG = WsManager.class.getSimpleName();
    private ToastUntil toastUntil= new ToastUntil(App.getContext());

    /**
     * websocket config
     */
    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int CONNECT_TIMEOUT = 5000;

    private WsStatus wsStatus;
    private WebSocket webSocket;
    private WsListener mWsListener;


    private WsManager() {
    }

    public static WsManager getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (WsManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WsManager();
                }
            }
        }
        return INSTANCE;
    }

    public void init() {
        if (webSocket != null && webSocket.isOpen()) {
            return;
        }
        try {
            webSocket = new WebSocketFactory().createSocket(C.BASE_PATH, CONNECT_TIMEOUT)
                    .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大为5
                    .setMissingCloseFrameAllowed(false)//设置服务器端不允许关闭连接时却不发送关闭帧
                    .addListener(mWsListener = new WsListener())//添加回调监听
                    .connectAsynchronously();//异步链接
        } catch (IOException e) {
            EventBus.getDefault().postSticky(new WebSocketConnectionErrorEvent("WebSocket链接IO错误:" + e.getMessage()));
        }
        Log.i(TAG, "第一次链接-------------------------------》");
    }

    public WsStatus getWsStatus() {
        return wsStatus;
    }

    public void setWsStatus(WsStatus wsStatus) {
        this.wsStatus = wsStatus;
    }

    public void disConnect() {
        if (webSocket != null) {
            webSocket.disconnect();
        }
    }

    private Handler mHandler = new Handler();

    private volatile int reConnectCount = 0;//重连服务器次数
    private volatile int reSendMsgCount = 0;
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔

    /**
     * 重连
     */
    public void reConnect() {
        if (!CheckNetUntil.is_connction(App.getContext())) {
            reConnectCount = 0;
            Log.e(TAG, "reConnect: 重连失败，当前网络不可用");
            toastUntil.ShowToastShort("当前网络不可用");
            return;
        }
        if (webSocket != null &&
                !webSocket.isOpen() &&
                getWsStatus() != WsStatus.CONNECTING) {
            reConnectCount++;

            if (reConnectCount > C.RECONNECT_TIME) {
                toastUntil.ShowToastShort("重连失败，服务器断开链接");
                setWsStatus(WsStatus.CONNECT_FAILE);
                reConnectCount = 0;
                cancelReconnect();
                return;
            }
            setWsStatus(WsStatus.CONNECTING);

            long reConnectTime = minInterval;
            if (reConnectCount > 3) {
                long temp = minInterval * (reConnectCount - 2);
                reConnectTime = temp > maxInterval ? maxInterval : temp;
            }
            Log.e(TAG, "准备开始第" + reConnectCount + "次重连，间隔为：" + reConnectTime);
            toastUntil.ShowToastShort("服务器连接失败，正在重试第"+reConnectCount+"次重连......");
            mHandler.postDelayed(mReconnectTask, reConnectTime);

        }
    }

    private Runnable mReconnectTask = new Runnable() {
        @Override
        public void run() {
            try {
                webSocket = new WebSocketFactory().createSocket(C.BASE_PATH, CONNECT_TIMEOUT)
                        .setFrameQueueSize(FRAME_QUEUE_SIZE)
                        .setMissingCloseFrameAllowed(false)
                        .addListener(mWsListener = new WsListener())
                        .connectAsynchronously();
            } catch (IOException e) {
//                e.printStackTrace();
                EventBus.getDefault().postSticky(new WebSocketConnectionErrorEvent("Websocket链接IO错误：" + e.getMessage()));
            }
        }
    };

    /**
     * 取消重连
     */
    public void cancelReconnect() {
        reConnectCount = 0;
        mHandler.removeCallbacks(mReconnectTask);
    }


    public synchronized void sendMsg(String msg) {
        Log.e(TAG, "sendMsg1:---------------------> ");
        if (msg == null) return;
        if (webSocket != null && wsStatus == WsStatus.CONNECT_SUCCESS) {
            webSocket.sendText(msg);
            reSendMsgCount = 0;
        } else {

            if (reSendMsgCount >= C.RECONNECT_TIME) {
                reSendMsgCount = 0;
                return;
            }
            reSendMsgCount++;
            reConnect();
            sendMsg(msg);
        }

    }

    //    /**
//     * 判断网络是否链接
//     *
//     * @return
////     */
//    private boolean isNetConnect() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//            if (info != null && info.isConnected()) {
//                //当前网络链接
//                if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
