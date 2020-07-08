//package com.yogo.conversation;
//
//import com.google.gson.Gson;
//import com.yogo.service.ConversationService;
//import com.yogo.service.ServiceGroupPeople;
//import com.yogo.service.WorkTimeService;
//import com.yogo.service.resolver.ResolverFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.server.standard.SpringConfigurator;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.util.Vector;
//
///**
// * Created by Lee on 2017/7/12.
// */
//@ServerEndpoint(value="/ServiceWS")
//@Component
//public class ServiceWS implements WebSocket {
//
//    @Autowired
//    private WorkTimeService workTimeService;
//
//    @Autowired
//    private ConversationService conversationService;
//
//    private ResolverFactory resolverFactory;
//    @Autowired
//	public void setResolverFactory(ResolverFactory resolverFactory){
//		this.resolverFactory = resolverFactory;
//	}
//
//
//    @Autowired
//    private ServiceGroupPeople serviceGroupPeople;
//
//    public static Vector<WebSocket> wsVector = new Vector<WebSocket>();
//
//    private Session session;
//
//    private int clientId;
//
//    private int serviceId = 1;
//
//    @OnOpen
//    public void onOpen(Session session){
//        this.session = session;
//        wsVector.add(this);
//    }
//
//    @OnMessage
//    public void onMessage(String msgString){
//        System.out.println("收到消息" + msgString);
//        Gson gson = new Gson();
//        try {
//            resolverFactory.doAction(msgString, this);
//        }catch (NullPointerException e){
//            System.out.println("----------------------"+e.getMessage());
//        }
//    }
//
//    @OnError
//    public void onError(Throwable t){
//        System.out.println("service" +t);
//        workTimeService.offline(serviceId);
//        conversationService.finishAllByServiceId(serviceId);
//        wsVector.remove(this);
//        serviceGroupPeople.quit(serviceId);
//    }
//
//    @OnClose
//    public void onClose(){
//        System.out.println("Service close");
//        workTimeService.offline(serviceId);
//        conversationService.finishAllByServiceId(serviceId);
//        wsVector.remove(this);
//        serviceGroupPeople.quit(serviceId);
//    }
//
//    public Vector<WebSocket> getWsVector() {
//        return wsVector;
//    }
//
//    public Session getSession() {
//        return session;
//    }
//
//    public int getClientId() {
//        return clientId;
//    }
//
//    public int getServiceId() {
//        return serviceId;
//    }
//
//    public void setClientId(int clientId) {
//        this.clientId = clientId;
//    }
//
//    public void setServiceId(int serviceId) {
//        this.serviceId = serviceId;
//    }
//}
