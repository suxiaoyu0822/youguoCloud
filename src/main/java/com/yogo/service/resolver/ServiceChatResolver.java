package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.Message;
import com.yogo.bean.ServiceChat;
import com.yogo.conversation.ws.ClientWS;
import com.yogo.conversation.WebSocket;
import com.yogo.service.ChatLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 客服聊天解析程序
 * Created by Lee on 2017/7/15.
 */
@Service
public class ServiceChatResolver implements ContentResolver{

    @Autowired
    private ChatLogService chatLogService;

    private Gson gson = new Gson();

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("----------------------------客服聊天解析程序----------------------------");
        Session session = webSocket.getSession();
        System.out.println("接收的消息："+msgJson);
        Type objectType = new TypeToken<Message<ServiceChat>>(){}.getType();
        Message<ServiceChat> message = gson.fromJson(msgJson, objectType);
        message.getContent().setTime(new Date().getTime());
        message.getContent().setServiceId(webSocket.getServiceId());
        ServiceChat serviceChat = message.getContent();
        System.out.println("聊天内容："+message.getContent());
        int contentType = serviceChat.getContentType();
        if (contentType == 0){
            chatLogService.addWithConversationId
                    (serviceChat.getConversationId(),webSocket.getServiceId(),serviceChat.getClientId(),contentType,serviceChat.getContent(),new Date().getTime(),0);
            try {
                System.out.println("serviceChatResolver 4");
                session.getBasicRemote().sendText(gson.toJson(message));
                for(WebSocket ws : ClientWS.wsVector){
                    if (ws.getClientId() == serviceChat.getClientId()){
                        System.out.println("serviceChatResolver 5" + ws.getClientId());
                        System.out.println("aaa：" + ws.getClientId() + " " +ws.getSession() + " " +ws.getSession().getBasicRemote());
                        ws.getSession().getBasicRemote().sendText(gson.toJson(message));
                        System.out.println("serviceChatResolver 6");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
