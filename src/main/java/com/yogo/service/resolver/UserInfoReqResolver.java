package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.Message;
import com.yogo.bean.UserInfoReq;
import com.yogo.bean.UserInfoResp;
import com.yogo.conversation.WebSocket;
import com.yogo.entity.Client;
import com.yogo.entity.CustomerService;
import com.yogo.entity.JoinUp;
import com.yogo.service.ClientService;
import com.yogo.service.CustomerServiceService;
import com.yogo.service.JoinUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Lee on 2017/7/14.
 */
@Service
public class UserInfoReqResolver implements ContentResolver {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private JoinUpService joinUpService;

    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("==========UserInfo");
        Session session = webSocket.getSession();
        Gson gson = new Gson();
        Type objectType = new TypeToken<Message<UserInfoReq>>(){}.getType();
        Message<UserInfoReq> message = gson.fromJson(msgJson, objectType);
        System.out.println("4444444444444444444444"+message.getContent().toString());
        UserInfoReq userInfoReq = message.getContent();
        int userType = userInfoReq.getUserType();
        int userId = userInfoReq.getUserId();
        String nickName;
        if (userInfoReq.getUserType() == 0){
            Client client = clientService.selectClientByClientId(userId);
            if (client.getName() == null){
                JoinUp joinUp = joinUpService.getLastByClientId(userId);
                nickName = joinUp.getAccess().getName() +  "用户" + joinUp.getAccount();
            } else {
                nickName = client.getName();
            }
        } else {
            System.out.println("&&&&&&&&&&&&&&&&&&&userId:"+userId);
            CustomerService customerService = customerServiceService.selectCustomerServiceByServiceId(userId);
            System.out.println(customerService.toString());
            nickName = customerService.getName();
        }
        UserInfoResp userInfoResp = new UserInfoResp(userType, nickName, userId);
        Message<UserInfoResp> res = new Message<UserInfoResp>(userInfoResp);
        try {
            session.getBasicRemote().sendText(gson.toJson(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}