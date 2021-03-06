package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.ClientLogin;
import com.yogo.bean.ConversationStart;
import com.yogo.bean.Message;
import com.yogo.bean.ServiceChat;
import com.yogo.conversation.WebSocket;
import com.yogo.entity.JoinUp;
import com.yogo.service.ChatLogService;
import com.yogo.service.ClientService;
import com.yogo.service.ConversationService;
import com.yogo.service.JoinUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 客户端登录解析程序
 * Created by Lee on 2017/7/14.
 */
@Service
public class ClientLoginResolver implements ContentResolver {

    @Autowired
    private JoinUpService joinUpService;

    @Autowired
    private ChatLogService chatLogService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ClientService clientService;

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("----------------------------客户端登录解析程序----------------------------");
        System.out.println("接收的消息："+msgJson);
        Session session = webSocket.getSession();
        ConversationStart conversationStart = new ConversationStart();
        Gson gson = new Gson();
        Type type = new  TypeToken<Message<ClientLogin>>(){}.getType();
        Message<ClientLogin> message = gson.fromJson(msgJson, type);
        ClientLogin clientLogin = message.getContent();
        List<JoinUp> joinUpList = null;
        String account = null;
        int clientId = 0;
        int accessId = clientLogin.getAccessId();
        if (clientLogin.getAccount() != null){
            System.out.println("Account不为空");
            joinUpList = joinUpService.hasJoinedUp(clientLogin.getAccessId(), clientLogin.getAccount());
            System.out.println("joinUpList:"+joinUpList);
            if (joinUpList != null){
                account = joinUpList.get(0).getAccount();
                clientId = joinUpList.get(0).getClientId();
                joinUpService.addJoinUp(accessId, clientId, new Date().getTime(),account);
                conversationStart.setChatLogList(chatLogService.getByClientId(clientId));
            }
        } else {
            System.out.println("Account为空");
            account = getNewAccount(accessId);
            clientId = clientService.insertClient(null, null, null, null, 0);
            joinUpService.addJoinUp(accessId, clientId, new Date().getTime(),account);
        }
        conversationStart.setAccount(account);
        conversationStart.setClientId(clientId);
        System.out.println("ws serviceid:"+webSocket.getServiceId());
        conversationService.startConversation(clientId, 0, new Date().getTime());
        conversationStart.setConversationId(conversationService.getLastIdByClientId(clientId));
        Message<ConversationStart> ret = new Message<ConversationStart>(conversationStart);
        webSocket.setClientId(clientId);
        try {
            session.getBasicRemote().sendText(gson.toJson(ret));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServiceChat serviceChat = new ServiceChat();
        serviceChat.setServiceId(0);
        serviceChat.setTime(new Date().getTime());
        serviceChat.setClientId(clientId);
        serviceChat.setContentType(0);
        serviceChat.setContent("您好，我是智能客服，请问有什么可以帮助您的吗？");
        try {
            System.out.println(serviceChat);
            session.getBasicRemote().sendText(gson.toJson(new Message<ServiceChat>(serviceChat)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNewAccount(int accessId){
        String account = "";
        Random random = new Random();
        do {
            for (int i = 0; i < 10; i++){
                account += random.nextInt(10);
            }
            System.out.println("aaa" + (joinUpService.hasJoinedUp(accessId, account).size() == 0));
        } while (joinUpService.hasJoinedUp(accessId, account).size() != 0);
        return account;
    }
}
