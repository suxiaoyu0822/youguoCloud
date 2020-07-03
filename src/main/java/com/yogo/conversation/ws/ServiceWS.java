package com.yogo.conversation.ws;

import com.google.gson.Gson;
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

	@Autowired
	private WorkTimeService workTimeService;

	@Autowired
	private ConversationService conversationService;

	private ResolverFactory resolverFactory = new ResolverFactory();

	@Autowired
	private ServiceGroupPeople serviceGroupPeople;

	public static Vector<WebSocket> wsVector = new Vector<WebSocket>();

	private Session session;

	private int clientId;

	private int serviceId = 1;

	@OnOpen
	public void onOpen(Session session){
		System.out.println("客服握哈手");
		this.session = session;
		wsVector.add(this);
	}

	@OnMessage
	public void onMessage(String msgString){
		System.out.println("收到消息" + msgString);
		Gson gson = new Gson();
		try {
			resolverFactory.doAction(msgString, this);
		}catch (Exception e){
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
		workTimeService.offline(serviceId);
		conversationService.finishAllByServiceId(serviceId);
		wsVector.remove(this);
		serviceGroupPeople.quit(serviceId);
	}

	public Vector<WebSocket> getWsVector() {
		return wsVector;
	}

	public Session getSession() {
		return session;
	}

	public int getClientId() {
		return clientId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
}
