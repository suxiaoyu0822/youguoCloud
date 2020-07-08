package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by Lee on 2017/7/12.
 */
@Service
public class ResolverFactory {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ResolverFactory.applicationContext = applicationContext;
    }

    public void doAction(String msgJson, WebSocket webSocket){
        System.out.println("doAction");
        Gson gson = new Gson();
        Message message = gson.fromJson(msgJson, Message.class);
        String type = message.getType() + "Resolver";
        System.out.println(type);
//        ((ContentResolver)SpringUtil.getBean(type)).resolve(msgJson, webSocket);
        ((ContentResolver)applicationContext.getBean(type)).resolve(msgJson, webSocket);
    }
}
