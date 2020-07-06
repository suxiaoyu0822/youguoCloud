package com.yogo.service;
import com.yogo.entity.ChatLog;
import com.yogo.entity.ChatLogAndSendRecInfo;

import java.util.List;

/**
 * Created by Lee on 2017/7/13.
 */
public interface ChatLogService {

    int add(int senderId, int receiverId, int contentType, String content, Long time, int fromClient);

    int addWithConversationId(int conversationId, int senderId, int receiverId, int contentType, String content, Long time, int fromClient);

    List<ChatLog> getByClientId(int clientId);

    List<ChatLog> getByConversationId(int conversationId);

    List<ChatLog> getClientSayByConversationId(int conversationId);
    
    List<ChatLogAndSendRecInfo> selectClientAndServerChatLog(int clientId, int serviceId);
    
    List<ChatLogAndSendRecInfo> selectClientChatLog(int clientId);
}
