package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.ClientDetailReq;
import com.yogo.bean.ClientDetailResp;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import com.yogo.entity.Client;
import com.yogo.entity.Flag;
import com.yogo.entity.JoinUp;
import com.yogo.service.ClientService;
import com.yogo.service.JoinUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端请求详细信息解析程序
 * Created by Administrator on 2017/7/15.
 */
@Service
public class ClientDetailReqResolver implements ContentResolver {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JoinUpService joinUpService;

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket){
        System.out.println("----------------------------客户端请求详细信息解析程序----------------------------");
        Session session = webSocket.getSession();
        Gson gson = new Gson();
        Type objectType = new TypeToken<Message<ClientDetailReq>>(){}.getType();
        System.out.println("接收的消息:"+msgJson);
        Message<ClientDetailReq> message = gson.fromJson(msgJson,objectType);
        ClientDetailReq clientDetailReq = message.getContent();
        System.out.println("ClientDetailReq:"+clientDetailReq.toString());
        int clientId = clientDetailReq.getClientId();
        Client client = clientService.selectClientByClientId(clientId);
        String clientName = client.getName();
        int sex = client.getSex();
        Long phoneNum = client.getTelephone();
        String email = client.getEmail();
        String address = client.getAddress();
        String wx = "";
        String qq = "";
        String weibo = "";
        List<JoinUp> joinUpList = joinUpService.getByClientId(clientId);
        for (JoinUp aJoinUpList : joinUpList) {
            if (aJoinUpList.getAccess().getName().equals("微信")) {
                wx = aJoinUpList.getAccount();
            }
            if (aJoinUpList.getAccess().getName().equals("QQ")) {
                qq = aJoinUpList.getAccount();
            }
            if (aJoinUpList.getAccess().getName().equals("微博")) {
                weibo = aJoinUpList.getAccount();
            }
        }
        List<Flag> flagList = clientService.selectAllFlag(clientId);
        List<String> tagList = new ArrayList<String>();
        if(flagList != null && flagList.size() > 0){
            for(int i=0;i<flagList.size();i++){
                tagList.add(flagList.get(i).getName());
            }
        }
        List<Flag> unusedFlagList = clientService.selectAllUnusedFlag(clientId);
        List<String> unusedTagList = new ArrayList<String>();
        if(unusedFlagList != null && unusedFlagList.size() > 0){
            for(int i=0;i<unusedFlagList.size();i++){
                unusedTagList.add(unusedFlagList.get(i).getName());
            }
        }
        ClientDetailResp clientDetailResp = new ClientDetailResp(clientId,clientName,sex,phoneNum,email,wx,qq,weibo,address,tagList,unusedTagList);
        Message<ClientDetailResp> res = new Message<ClientDetailResp>(clientDetailResp);
        try{
            session.getBasicRemote().sendText(gson.toJson(res));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
