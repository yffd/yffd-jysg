package com.yffd.jysg.springboot.demo.websocket.controller;

import com.yffd.jysg.springboot.demo.websocket.vo.WiselyMessage;
import com.yffd.jysg.springboot.demo.websocket.vo.WiselyReponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @MessageMapping注解开启WebSocket的访问地址映射；
 * 当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似@RequestMapping；
 * 当服务端有消息存在时，会对订阅@SendTo中路径的浏览器发送请求；
 */
@Controller
public class WsController {

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public WiselyReponse say(WiselyMessage message) throws Exception {
        //等待3秒返回消息内容
        Thread.sleep(3000);
        return new WiselyReponse("欢迎使用webScoket："+message.getName());
    }
}
