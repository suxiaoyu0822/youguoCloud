package com.yogo.conversation.ws;

import com.google.gson.Gson;
import com.yogo.bean.ConversationEndSignal;
import com.yogo.bean.Message;
import com.yogo.bean.ServiceStatus;
import com.yogo.conversation.WebSocket;
import com.yogo.dao.ConversationMapper;
import com.yogo.service.ConversationService;
import com.yogo.service.GroupQueue;
import com.yogo.service.resolver.ResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

@ServerEndpoint(value = "/ClientWS")
@Component
public class ClientWS implements WebSocket {

	private ResolverFactory resolverFactory = new ResolverFactory();

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private ConversationMapper conversationMapper;

	@Autowired
	private GroupQueue groupQueue;

	private Session session;

	public static Vector<WebSocket> wsVector = new Vector<WebSocket>();

	private int clientId;

	private int serviceId;

	private Gson gson = new Gson();

	@OnOpen
	public void onOpen(Session session){
		System.out.println("客户握哈手");
		this.session = session;
		wsVector.add(this);

	}

	@OnMessage
	public void onMessage(String msgString) throws Exception {
		System.out.println("收到消息：" + msgString);
		System.out.println(session);
		System.out.println(resolverFactory + "!!");
		try {
			resolverFactory.doAction(msgString, this);
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("onMessage异常================"+e);
		}
	}

	@OnClose
	public void onClose(){
		System.out.println("Client!!!close");
		int conversationId = conversationService.getLastIdByClientId(clientId);
		conversationService.endConversation(conversationId, new Date().getTime(), null);
		//给客服发会话结束信号
		for (WebSocket sws : ServiceWS.wsVector){
			if (sws.getServiceId() == serviceId){
				try {
					sws.getSession().getBasicRemote().sendText(gson.toJson(new Message<ConversationEndSignal>(new ConversationEndSignal(conversationId))));
					ServiceStatus serviceStatus = new ServiceStatus();
					serviceStatus.setConversationCount(conversationMapper.selectNotFinishByServiceId(serviceId));
					sws.getSession().getBasicRemote().sendText(gson.toJson(new Message<ServiceStatus>(serviceStatus)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		groupQueue.removeByClient(clientId);
		wsVector.remove(this);
	}

	@OnError
	public void onError(Throwable t){
		System.out.println(t.getCause() + "!!!error");
		int conversationId = conversationService.getLastIdByClientId(clientId);
		conversationService.endConversation(conversationId, new Date().getTime(), null);
		//给客服发会话结束信号
		for (WebSocket sws : ServiceWS.wsVector){
			if (sws.getServiceId() == serviceId){
				try {
					sws.getSession().getBasicRemote().sendText(gson.toJson(new Message<ConversationEndSignal>(new ConversationEndSignal(conversationId))));
					ServiceStatus serviceStatus = new ServiceStatus();
					serviceStatus.setConversationCount(conversationMapper.selectNotFinishByServiceId(serviceId));
					sws.getSession().getBasicRemote().sendText(gson.toJson(new Message<ServiceStatus>(serviceStatus)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		groupQueue.removeByClient(clientId);
		wsVector.remove(this);
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public ResolverFactory getResolverFactory() {
		return resolverFactory;
	}

	public Session getSession() {
		return session;
	}

	public Vector<WebSocket> getWsVector(){
		return wsVector;
	}
}