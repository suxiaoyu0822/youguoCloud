package com.yogo.conversation.ws;

import com.yogo.conversation.WebSocket;
import com.yogo.service.ConversationService;
import com.yogo.service.ServiceGroupPeople;
import com.yogo.service.WorkTimeService;
import com.yogo.service.resolver.ResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Vector;

@ServerEndpoint(value = "/ServiceWS")
@Component
public class ServiceWS implements WebSocket {

	private static WorkTimeService workTimeService;
	@Autowired
	public void setWorkTimeService(WorkTimeService workTimeService) {
		this.workTimeService = workTimeService;
	}

	private static ConversationService conversationService;
	@Autowired
	public void setConversationService(ConversationService conversationService) {
		this.conversationService = conversationService;
	}

	private static ResolverFactory resolverFactory;
	@Autowired
	public void setResolverFactory(ResolverFactory resolverFactory){
		this.resolverFactory = resolverFactory;
	}

	private static ServiceGroupPeople serviceGroupPeople;
	@Autowired
	public void setServiceGroupPeople(ServiceGroupPeople serviceGroupPeople) {
		this.serviceGroupPeople = serviceGroupPeople;
	}

	public static Vector<WebSocket> wsVector = new Vector<WebSocket>();

	private static Session session;

	private static int clientId;

	private static int serviceId;

	@OnOpen
	public void onOpen(Session session){
		System.out.println("客服握哈手");
		this.session = session;
		wsVector.add(this);
	}

	@OnMessage
	public void onMessage(String msgString){
		System.out.println("收到消息" + msgString);
		try {
			resolverFactory.doAction(msgString, this);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("================"+e);
		}
	}

	@OnError
	public void onError(Throwable t){
		System.out.println("+++++++service" +t);
		workTimeService.offline(serviceId);
		conversationService.finishAllByServiceId(serviceId);
		wsVector.remove(this);
		serviceGroupPeople.quit(serviceId);
	}

	@OnClose
	public void onClose(){
		System.out.println("Service close");
		System.out.println("ServiceId:"+serviceId);
		workTimeService.offline(serviceId);
		conversationService.finishAllByServiceId(serviceId);
		wsVector.remove(this);
		serviceGroupPeople.quit(serviceId);
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public Vector<WebSocket> getWsVector() {
		return wsVector;
	}

	@Override
	public int getClientId() {
		return clientId;
	}

	@Override
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public int getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
}
