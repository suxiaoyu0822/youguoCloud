package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 解析程序工厂
 * Created by Lee on 2017/7/12.
 */
@Service
public class ResolverFactory {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ResolverFactory.applicationContext = applicationContext;
    }

    public void doAction(String msgJson, WebSocket webSocket){
        System.out.println("----------------------------解析程序工厂----------------------------");
        Gson gson = new Gson();
        Message message = gson.fromJson(msgJson, Message.class);
        String type = message.getType() + "Resolver";
        System.out.println("将要调用的解析程序："+type);
//        ((ContentResolver)SpringUtil.getBean(type)).resolve(msgJson, webSocket);
        ((ContentResolver)applicationContext.getBean(type)).resolve(msgJson, webSocket);
    }
}
