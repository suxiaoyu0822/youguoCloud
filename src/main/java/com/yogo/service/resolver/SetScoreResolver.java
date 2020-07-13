package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.Message;
import com.yogo.bean.SetScore;
import com.yogo.conversation.WebSocket;
import com.yogo.dao.ConversationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.lang.reflect.Type;

/**
 * 设置核心解析程序
 * Created by Lee on 2017/7/18.
 */
@Service
public class SetScoreResolver implements ContentResolver {

    private Gson gson = new Gson();

    @Autowired
    private ConversationMapper conversationMapper;

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("----------------------------设置核心解析程序----------------------------");
        Session session = webSocket.getSession();
        System.out.println("接收的消息："+msgJson);
        Type objectType = new TypeToken<Message<SetScore>>(){}.getType();
        Message<SetScore> message = gson.fromJson(msgJson, objectType);
        SetScore setScore = message.getContent();
        int score = setScore.getScore();
        int conversationId = setScore.getConversationId();
        System.out.println("核心：" + score+" +++++" + conversationId);
        System.out.println("会话Mapper："+conversationMapper);
        conversationMapper.updateScore(conversationId,score);
        webSocket.setServiceId(0);
    }
}
