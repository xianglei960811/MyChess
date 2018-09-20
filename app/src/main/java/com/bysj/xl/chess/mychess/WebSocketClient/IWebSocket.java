package com.bysj.xl.chess.mychess.WebSocketClient;

/**
 * socket接口
 * 2018年9月17日
 */
public interface IWebSocket {

    /**
     * 发送数据
     * @param msg
     * @return
     */
    void sendMsg(String msg);

    /**
     * 0 - 未连接
     * 1 - 正在连接
     * 2 - 已连接
     * @return
     */
    int getConnectStatus();

    /**
     * 重新连接
     */
    void reConnect();

    /**
     * 关闭连接
     */
    void stop();

    /**
     * 获取服务地址接口
     * @return
     */
    String getConnectUrl();
}
