package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.AddTag;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import com.yogo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/7/17.
 */
@Service
public class AddTagResolver implements ContentResolver {

    @Autowired
    private ClientService clientService;

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket) {
        Gson gson = new Gson();
        Type objectType = new TypeToken<Message<AddTag>>(){}.getType();
        Message<AddTag> message = gson.fromJson(msgJson,objectType);
        AddTag addTag = message.getContent();
        int clientId = addTag.getClientId();
        String tag = addTag.getTag();
        clientService.addFlag(clientId,tag);
    }
}
