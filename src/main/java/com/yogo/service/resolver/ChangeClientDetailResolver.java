package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.ChangeClientDetail;
import com.yogo.bean.ClientDetailResp;
import com.yogo.bean.Message;
import com.yogo.conversation.WebSocket;
import com.yogo.entity.Flag;
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
 * 更改客户端详细信息解析程序
 * Created by Administrator on 2017/7/16.
 */
@Service
public class ChangeClientDetailResolver implements ContentResolver {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JoinUpService joinUpService;

    @Transactional
    public void resolve(String msgJson, WebSocket webSocket){
        System.out.println("----------------------------更改客户端详细信息解析程序----------------------------");
        Session session = webSocket.getSession();
        System.out.println("接收的消息："+msgJson);
        Gson gson = new Gson();
        Type objectType = new TypeToken<Message<ChangeClientDetail>>(){}.getType();
        Message<ChangeClientDetail> message = gson.fromJson(msgJson,objectType);
        ChangeClientDetail changeClientDetail = message.getContent();
        int clientId = changeClientDetail.getClientId();
        String clientName = changeClientDetail.getClientName();
        int sex = changeClientDetail.getSex();
        Long phoneNum = changeClientDetail.getPhoneNum();
        String email = changeClientDetail.getEmail();
        String address = changeClientDetail.getAddress();
        String wx = changeClientDetail.getWx();
        String qq = changeClientDetail.getQq();
        String weibo = changeClientDetail.getWeibo();
        clientService.updateClient(clientId,clientName,address,email,phoneNum,sex);
        joinUpService.updateAllJoinUp(clientId,qq,wx,weibo);
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
        try {
            session.getBasicRemote().sendText(gson.toJson(res));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}