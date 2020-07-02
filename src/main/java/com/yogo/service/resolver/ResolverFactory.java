package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import com.yogo.util.SpringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Lee on 2017/7/12.
 */
@Service
@Component
public class ResolverFactory implements ApplicationContextAware {
    public ResolverFactory() {
        System.out.println("初始化对象");
    }

    @Autowired
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void doAction(String msgJson, WebSocket webSocket){
//        System.out.println("doAction");
        Gson gson = new Gson();
        Message message = gson.fromJson(msgJson, Message.class);
        String type = message.getType() + "Resolver";
        System.out.println(type);
        ((ContentResolver)SpringUtil.getBean(type)).resolve(msgJson, webSocket);

    }
}
