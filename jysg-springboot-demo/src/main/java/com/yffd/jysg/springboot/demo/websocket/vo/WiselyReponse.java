package com.yffd.jysg.springboot.demo.websocket.vo;

/**
 * 服务端广播通知浏览器的实体
 */
public class WiselyReponse {

    private String responseMessage;

    public WiselyReponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
