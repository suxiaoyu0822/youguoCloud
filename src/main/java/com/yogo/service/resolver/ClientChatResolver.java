package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.*;
import com.yogo.conversation.WebSocket;
import com.yogo.conversation.ws.ServiceWS;
import com.yogo.dao.ConversationMapper;
import com.yogo.entity.ChatLog;
import com.yogo.entity.CustomerService;
import com.yogo.entity.Flag;
import com.yogo.entity.Knowledge;
import com.yogo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户端聊天解析程序
 * Created by Lee on 2017/7/12.
 */
@Service
public class ClientChatResolver implements ContentResolver {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FlagService flagService;

    @Autowired
    private ChatLogService chatLogService;

    @Autowired
    private KnowledgeService knowledgeService;


    private static ConversationService conversationService;
    @Autowired
    public void setConversationService(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @Autowired
    private GroupWordService groupWordService;

    @Autowired
    private GroupQueue groupQueue;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private OnlineService onlineService;

    private Gson gson = new Gson();

    int Client_To_Robot = new Integer(0); //0-客户与机器人会话 1-客户与客服会话


    @Transactional
    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("----------------------------客户端聊天解析程序----------------------------");
        System.out.println("信息：" +msgJson + "getServiceid:"+webSocket.getServiceId() +"getClientid:"+webSocket.getClientId());
        Session session = webSocket.getSession();
//        System.out.println("聊天日志："+chatLogService.getByClientId(1));
        Type objectType = new TypeToken<Message<ClientChat>>(){}.getType();
        Message<ClientChat> message = gson.fromJson(msgJson, objectType);
        System.out.println("信息内容："+message.getContent().toString());
        message.getContent().setTime(new Date().getTime());
        ClientChat clientChat = message.getContent();
        int contentType = clientChat.getContentType();
        System.out.println("通过webSocket获取客服id："+webSocket.getServiceId());
        CustomerService customerService = customerServiceService.selectCustomerServiceByServiceId(webSocket.getServiceId());
        System.out.println("!!2");
        //判断目标客服是否在线
        Map<String, String> onlineServiceMap = onlineService.getMap();
        System.out.println("是否在线："+onlineServiceMap.toString());
        boolean targetIsOnline = false;
        for (String s : onlineServiceMap.keySet()){
            System.out.println("111111111111111"+s);
            System.out.println("111111111111111"+customerService);
            System.out.println("333333333333333"+onlineServiceMap.get(s));
            if (customerService!=null){
                if (customerService.getEmployeeId().equals(onlineServiceMap.get(s))){
                    targetIsOnline = true;
                    System.out.println("目标客服在线");
                }
            }
        }
        if (!targetIsOnline){
            System.out.println("目标客服已经不在线");
            webSocket.setServiceId(0);
        }
        if (contentType == 0){//如果该消息是文字消息
            System.out.println("!!3");
            chatLogService.addWithConversationId(clientChat.getConversationId(),clientChat.getClientId(),webSocket.getServiceId(),0,clientChat.getContent(), new Date().getTime(),1);
            System.out.println("3.5!!");
            if (webSocket.getServiceId() == 0){
                //如果该消息是发送给机器人的
                System.out.println("3.6!!");
                System.out.println("该消息是发送给机器人的");
                ClientToRobot(webSocket,message);
                if (clientChat.getContent() != null && clientChat.getContent().contains("转接到人工客服")){
                    Client_To_Robot = 1;
                    this.transfer(webSocket, clientChat);
                }
            } else {
                //如果该消息是发送给客服人员的
                System.out.println("1.1!!");
                System.out.println("该消息是发送给客服人员的");
                //给客服人员推荐标签
                System.out.println("0000000000000000"+clientChat.toString());
                this.recommandTags(webSocket,clientChat.getConversationId(),clientChat.getContent());
                System.out.println("1.2!!");
                List<Knowledge> knowledgeList = knowledgeService.getKnowledgeByContent(clientChat.getContent());
                System.out.println("1.3!!");
                Message<RecommandKnowledges> res = new Message<RecommandKnowledges>(new RecommandKnowledges(clientChat.getConversationId(),knowledgeList));
                System.out.println("1.4!!");
                try {
                    session.getBasicRemote().sendText(gson.toJson(message));
                    System.out.println("1.5!!");
                    System.out.println(ServiceWS.wsVector.size());
                    for (WebSocket sws : ServiceWS.wsVector){
                        System.out.println("UUUUUUUUUUUUUUUUUU"+sws.getServiceId());
                        if (clientChat.getContent() != null && clientChat.getContent().contains("转接到人工客服")){
                            System.out.println("老客户转人工！");
                            Client_To_Robot = 1;
                            this.transfer(webSocket, clientChat);
                        }
                        if (Client_To_Robot == 0){
                            ClientToRobot(webSocket,message);
                        }else if (sws.getServiceId() == webSocket.getServiceId()){
                            System.out.println("正常发送！");
                            sws.getSession().getBasicRemote().sendText(gson.toJson(message));
                            sws.getSession().getBasicRemote().sendText(gson.toJson(res));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //与机器人进行交流
    private void ClientToRobot(WebSocket webSocket, Message<ClientChat> message){
        Session session = webSocket.getSession();
        ClientChat clientChat = message.getContent();
        //机器人直接给客户打标签
        System.out.println("3.7!!");
        this.takeFlags(webSocket,clientChat.getContent());

        System.out.println("!!4");
        Message<RobotChat> res = knowledgeService.getRobotChat(clientChat.getContent());
        chatLogService.addWithConversationId(clientChat.getConversationId(),webSocket.getServiceId(), clientChat.getClientId(),0,res.getContent().getAnswer(),new Date().getTime(),0);
        try {
            System.out.println("!!5");
            session.getBasicRemote().sendText(gson.toJson(message));
            session.getBasicRemote().sendText(gson.toJson(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //转发到客服
    private void transfer(WebSocket webSocket, ClientChat clientChat){
        System.out.println("7");
        System.out.println("8");
        System.out.println("找到的ClientId："+clientChat.getClientId());
        System.out.println("在线客服："+webSocket.getServiceId());
        CustomerService target = conversationService.getLastChatServiceId(clientChat.getClientId());
        System.out.println("找到客服：" + target);
        System.out.println("9");
        //找之前聊过天的客服
        WebSocket targetWS = null;
        if (target != null){
            System.out.println("wsVector大小:"+ServiceWS.wsVector.size());
            for (int i = 0; i < ServiceWS.wsVector.size(); i++){
                System.out.println("cnt1");
                System.out.println("cnt2"+ServiceWS.wsVector.get(i).getServiceId());
                System.out.println("cnt3"+target.getServiceId());
                if (ServiceWS.wsVector.get(i).getServiceId() == target.getServiceId()){
                    targetWS = ServiceWS.wsVector.get(i);
                }
            }
        }
        if (targetWS != null){//如果存在之前聊过天的客服，则接入到该客服
            System.out.println("有聊过的客服！！！！！！！");
            conversationService.resetServiceId(target.getServiceId(), clientChat.getConversationId());
            Message<ServiceChat> message =
                    new Message<ServiceChat>(new ServiceChat(clientChat.getConversationId(),clientChat.getClientId(),0, target.getAutoMessage(),new Date().getTime(), target.getServiceId()));
            try {
                webSocket.setServiceId(target.getServiceId());
                //将自动发送的消息添加入聊天记录并且存入数据库
                chatLogService.addWithConversationId(clientChat.getConversationId(),target.getServiceId(),clientChat.getClientId(),0, target.getAutoMessage(),new Date().getTime(),0);
                webSocket.getSession().getBasicRemote().sendText(gson.toJson(message));
                conversationService.resetServiceId(target.getServiceId(), clientChat.getConversationId());
                TransferSignal transferSignal = new TransferSignal(clientChat.getConversationId(),clientChat.getClientId(),chatLogService.getByClientId(clientChat.getClientId()));
                Message<TransferSignal> res = new Message<TransferSignal>(transferSignal);
                targetWS.getSession().getBasicRemote().sendText(gson.toJson(res));
                //将转接通知消息存入数据库
                notificationService.insertNotificationService(1,3,target.getServiceId(),"编号为" +clientChat.getClientId() +"的客户接入到会话中");
                //更新客服状态
                ServiceStatus serviceStatus = new ServiceStatus();
                serviceStatus.setConversationCount(conversationMapper.selectNotFinishByServiceId(target.getServiceId()));
                targetWS.getSession().getBasicRemote().sendText(gson.toJson(new Message<ServiceStatus>(serviceStatus)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("没有聊过的客服！！！！！！！");
            List<ChatLog> chatLogList = chatLogService.getClientSayByConversationId(clientChat.getConversationId());
            String content = "";
            for (ChatLog c : chatLogList){
                content += c.getContent();
            }
            System.out.println("!!!!!!!!!");
            int serviceGroupId = groupWordService.getServiceGroupIdByContent(content);
            System.out.println("serviceGroupId:"+serviceGroupId);
            if (serviceGroupId != 0){
                groupQueue.joinQueueByGroupId(serviceGroupId, clientChat.getClientId());
            } else {
                System.out.println("clientChat:"+clientChat);
                groupQueue.joinLeastClientQueue(clientChat.getClientId());
            }
        }
    }
    //机器人直接给客户打标签
    private void takeFlags(WebSocket webSocket, String content){
        List<Flag> list = flagService.getFlagByContent(content);
        System.out.println("机器人给客户打标签:"+list.toString());
        if(list != null && list.size() > 0){
            for(int i=0;i<list.size();i++){
                clientService.addFlag(webSocket.getClientId(),list.get(i).getName());
            }
        }
    }
    //给客服人员推荐标签
    private void recommandTags(WebSocket webSocket, int conversationId, String content){
        List<String> tagList = flagService.recommendFlags(webSocket.getClientId(),content);
        RecommandTags recommandTags = new RecommandTags(conversationId,tagList);
        Message<RecommandTags> res = new Message<RecommandTags>(recommandTags);
        try {
            for (WebSocket sws : ServiceWS.wsVector){
                if (sws.getServiceId() == webSocket.getServiceId()){
                    sws.getSession().getBasicRemote().sendText(gson.toJson(res));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
