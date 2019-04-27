package com.bysj.xl.chess.mychess.WebSocket.WsMsgManager;

import android.util.Log;

import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.WebSocket.WbManager.WsInterface;
import com.bysj.xl.chess.mychess.WebSocket.WbManager.WsManager;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsForgetPassSuccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsForgetPhoneSuccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsLogSuccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsRegSuccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsUserAllSUccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsUserPhoneSuccessEvent;
import com.bysj.xl.chess.mychess.WebSocket.WsException;
import com.bysj.xl.chess.mychess.entity.Response;
import com.bysj.xl.chess.mychess.entity.TencentUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;

/**
 * author:向磊
 * date:2018/12/21
 * Describe：服务器消息处理类
 */
public class RsponseMsgHandler extends MsgManager implements WsInterface {
    private final static String SUCCESS = "success";

    @Override
    protected void dispatchResponse(String responseMsg) {
        Log.e("消息处理者", "dispatchResponse: " + responseMsg);
        try {
            Type type = new TypeToken<Response<TencentUser>>() {
            }.getType();
            Response<TencentUser> response = new Gson().fromJson(responseMsg, type);
//            Response response = JSON.parseObject(responseMsg, new TypeReference<Response>() {
//            });
            if (response == null) {
                Log.e("消息处理者", "来自服务器消息为空");
                return;
            }
            String msg = response.getMsg();
            if (msg.equals(SUCCESS)) {
                dispatchMsg(response);
            } else {
                throw new WsException(msg);
            }
        } catch (Exception e) {
            Log.e("数据处理类", "数据解析错误----------------------》" + e.getMessage());
        }
    }

    /**
     * 分发事件
     *
     * @param response
     */
    private void dispatchMsg(Response response) {
        String type = response.getUsr_response_type();
        switch (type.substring(0, 3)) {
            case C.LoGING:
                EventBus.getDefault().postSticky(new WsLogSuccessEvent(response.getData()));
                Log.e("handler", "dispatchMsg: _>log*-" + response.getData());
                break;
            case C.SIGN_UP_MODE:
                EventBus.getDefault().postSticky(new WsRegSuccessEvent(response.getData()));
                break;
            case C.FORGET_MODE:
                if (response.getUsr_response_type().equals(C.FORGET_PHONE)) {
                    EventBus.getDefault().postSticky(new WsForgetPhoneSuccessEvent(response.getData()));
                } else if (response.getUsr_response_type().equals(C.FORGET_PASSWORD)) {
                    EventBus.getDefault().postSticky(new WsForgetPassSuccessEvent(response.getData()));
                }
                break;
            case C.user_MODE:
                if (type.equals(C.USE_ALL)) {
                    EventBus.getDefault().postSticky(new WsUserAllSUccessEvent());
                }else if (type.equals(C.USE_PHONE)){
                    EventBus.getDefault().postSticky(new WsUserPhoneSuccessEvent());
                }
                Log.e("q11111", "dispatchMsg: ------------------>" );
                break;
            default:
                break;
        }

    }

    /**
     * 发送消息对外接口
     *
     * @param msg
     */
    @Override
    public void sendMsg(String msg) {
        WsManager.getINSTANCE().sendMsg(msg);
    }
}
