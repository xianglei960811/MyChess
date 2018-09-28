package com.bysj.xl.chess.mychess.WebSocketClient.Service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.LogEvent.LogAccountIDEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.LogEvent.LogPhoneEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.LogEvent.LogQQEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.entity.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * author:向磊
 * date:2018/9/17
 * Describe:
 */
public class WebSocketSerice extends MyService {
//    @Override
//    protected String getConnectURl() {
//        return C.BASE_PATH;//返回服务器地址
//    }

    //数据处理;并分发事件
    @Override
    protected void dispatchResponse(String responseMsg) {
        //处理数据
        Log.e("sss", "dispatchResponse: dddd" );
        try {
            Response response = JSON.parseObject(responseMsg, new TypeReference<Response>() {
            });
//            Log.e("dispatchResponse", "dispatchResponse:response:: "+new Gson().toJson(response));
            if (response == null) {
                EventBus.getDefault().postSticky(new WebSocketErrorEvent("接收数据为空"));
                return;
            }
            String msg = response.getMsg();
            if ("log_success".equals(msg)) {
                //返回succes才分发事件
                distributeEvent(response);
            } else if (response.getUsr_response_type().equals(C.LOGIN_QQ)) {
                EventBus.getDefault().postSticky(new LogQQEvent(response.getMsg(),response.getData()));
            }
//            EventBus.getDefault().postSticky(responseMsg);
            msgHandler.msgHandler(msg);
        } catch (Exception e) {
            EventBus.getDefault().postSticky(new WebSocketErrorEvent("数据解析错误" + e.getMessage()));
        }
    }

    @Override
    public String getConnectUrl() {
        return C.BASE_PATH;
    }

    /**
     * 分发事件
     *
     * @param response
     */
    private void distributeEvent(Response response) {
        if (response.getUsr_response_type().equals(C.LOGIN_PHONE)) {
            EventBus.getDefault().postSticky(new LogPhoneEvent(response.getData()));
            return;
        }
        if (response.getUsr_response_type().equals(C.LOGIN_ACCONTID)){
            EventBus.getDefault().postSticky(new LogAccountIDEvent(response.getData()));
            return;
        }
    }
}
