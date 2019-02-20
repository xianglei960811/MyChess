package com.bysj.xl.chess.mychess.WebSocket;
 /**
  *                             _ooOoo_
  *                            o8888888o
  *                            88" . "88
  *                            (| -_- |)
  *                            O\  =  /O
  *                         ____/`---'\____
  *                       .'  \\|     |//  `.
  *                      /  \\|||  :  |||//  \
  *                     /  _||||| -:- |||||-  \
  *                     |   | \\\  -  /// |   |
  *                     | \_|  ''\---/''  |   |
  *                     \  .-\__  `-`  ___/-. /
  *                   ___`. .'  /--.--\  `. . __
  *                ."" '<  `.___\_<|>_/___.'  >'"".
  *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
  *               \  \ `-.   \_ __\ /__ _/   .-` /  /
  *          ======`-.____`-.___\_____/___.-`____.-'======
  *                             `=---='
  *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  *                     佛祖保佑        永无BUG
  *
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bysj.xl.chess.mychess.Application.App;
import com.bysj.xl.chess.mychess.WebSocket.WbManager.WsManager;

/**
 * author:向磊
 * date:2018/12/20
 * Describe: 自定义广播，用于监听网络变化
 */
public class NetStatusReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
            //获取网络链接管理器
            ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info!=null&&info.isConnected()){
                WsManager.getINSTANCE().reConnect();//网络链接后重连
            }
        }
    }
}
