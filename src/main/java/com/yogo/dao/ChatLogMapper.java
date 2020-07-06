package com.yogo.dao;

import com.yogo.entity.ChatLog;
import com.yogo.entity.ChatLogAndSendRecInfo;
import com.yogo.entity.ChatLogExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatLogMapper {
    long countByExample(ChatLogExample example);

    int deleteByExample(ChatLogExample example);

    int insert(ChatLog record);

    int insertSelective(ChatLog record);

    List<ChatLog> selectByExample(ChatLogExample example);

    int updateByExampleSelective(@Param("record") ChatLog record, @Param("example") ChatLogExample example);

    int updateByExample(@Param("record") ChatLog record, @Param("example") ChatLogExample example);

    List<ChatLog> selectByConversationId(int conversationId);

    List<ChatLog> selectByClientId(int clientId);

    List<ChatLog> selectClientSayByConversationId(int conversationId);
    
    List<ChatLogAndSendRecInfo> selectClientAndServerChatLog(@Param("clientId") int clientId, @Param("serviceId") int serviceId);
    
    List<ChatLogAndSendRecInfo> selectClientChatLog(@Param("clientId") int clientId);
}