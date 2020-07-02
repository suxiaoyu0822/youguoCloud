package com.yogo.service.resolver;


import com.yogo.conversation.WebSocket;

/**
 * 处理从前端接收的消息
 * Created by Lee on 2017/7/12.
 */
public interface ContentResolver{
    void resolve(String msgJson, WebSocket webSocket);
}
