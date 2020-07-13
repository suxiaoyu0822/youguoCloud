package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.AddTag;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import com.yogo.service.ClientService;
import com.yogo.service.FlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;

/**
 * 删除标记解析程序
 * Created by Administrator on 2017/7/17.
 */
@Service
public class DeleteTagResolver implements ContentResolver {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FlagService flagService;

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("----------------------------删除标记解析程序----------------------------");
        Gson gson = new Gson();
        System.out.println("接收的消息："+msgJson);
        Type objectType = new TypeToken<Message<AddTag>>(){}.getType();
        Message<AddTag> message = gson.fromJson(msgJson,objectType);
        AddTag addTag = message.getContent();
        int clientId = addTag.getClientId();
        String tag = addTag.getTag();
        int flagId = flagService.selectFlagId(tag);
        if(flagId != 0){
            clientService.deleteFlag(clientId,flagId);
        }
    }
}
