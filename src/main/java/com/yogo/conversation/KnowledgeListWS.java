//package com.yogo.conversation;
//
//import com.yogo.service.KnowledgeService;
//import com.yogo.service.resolver.ResolverFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.socket.server.standard.SpringConfigurator;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.util.Vector;
//
///**
// * Created by Lee on 2017/7/12.
// */
//@ServerEndpoint(value = "/knowledgeListWS", configurator = SpringConfigurator.class)
//public class KnowledgeListWS {
//    private static Vector<KnowledgeListWS> knowledgeListVector = new Vector<KnowledgeListWS>();
//
//    private Session session;
//
//    @Autowired
//    private KnowledgeService knowledgeService;
//
//    @Autowired
//    private ResolverFactory resolverFactory;
//
//    @OnOpen
//    public void onOpen(Session session, EndpointConfig config){
//        this.session = session;
//        System.out.println(config.getUserProperties());
//        knowledgeListVector.add(this);
//    }
//
//    @OnMessage
//    public void onMessage(String msg){
//        System.out.println("收到消息" + msg);
//
///*        Gson gson = new Gson();
//        try {
//            this.session.getBasicRemote().sendText(gson.toJson(knowledgeService.getKnowledgeByContent(msg)));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//    }
//
//    @OnError
//    public void onError(Throwable t){
//        knowledgeListVector.remove(this);
//    }
//
//    @OnClose
//    public void onClose(){
//        knowledgeListVector.remove(this);
//    }
//}
