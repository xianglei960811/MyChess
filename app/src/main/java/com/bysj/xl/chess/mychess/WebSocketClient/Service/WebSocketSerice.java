package com.bysj.xl.chess.mychess.WebSocketClient.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.WebSocketClient.MessageEvent.WebSocketErrorEvent;
import com.bysj.xl.chess.mychess.WebSocketClient.Service.MyService;
import com.bysj.xl.chess.mychess.entity.CommonResponse;

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

    //数据处理
    @Override
    protected void dispatchResponse(String responseMsg) {
        //处理数据
        try {


            CommonResponse<String> response = JSON.parseObject(responseMsg, new TypeReference<CommonResponse<String>>() {
            });
            if (response == null) {
                EventBus.getDefault().postSticky(new WebSocketErrorEvent("接收数据为空"));
                return;
            }
            EventBus.getDefault().postSticky(response);
        }catch (Exception e){
            EventBus.getDefault().postSticky(new WebSocketErrorEvent("数据解析错误"+e.getMessage()));
        }
    }

    @Override
    public String getConnectUrl() {
        return C.BASE_PATH;
    }
}
