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
 * 用户信息请求解析程序
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
        System.out.println("----------------------------用户信息请求解析程序----------------------------");
        Session session = webSocket.getSession();
        System.out.println("接收的消息："+msgJson);
        Gson gson = new Gson();
        Type objectType = new TypeToken<Message<UserInfoReq>>(){}.getType();
        Message<UserInfoReq> message = gson.fromJson(msgJson, objectType);
        System.out.println("聊天内容："+message.getContent().toString());
        UserInfoReq userInfoReq = message.getContent();
        int userType = userInfoReq.getUserType();
        int userId = userInfoReq.getUserId();
        String nickName;
        if (userInfoReq.getUserType() == 0){ //userType 0-客户，1-客服
            System.out.println("×××××××××××××响应客户信息×××××××××××××");
            System.out.println("客户Id:"+userId);
            Client client = clientService.selectClientByClientId(userId);
            if (client.getName() == null){
                JoinUp joinUp = joinUpService.getLastByClientId(userId);
                nickName = joinUp.getAccess().getName() +  "用户" + joinUp.getAccount();
            } else {
                nickName = client.getName();
            }
        } else {
            System.out.println("×××××××××××××响应客服信息×××××××××××××");
            System.out.println("客户Id:"+userId);
            if (userId==0){
                userId = 6;
            }
            CustomerService customerService = customerServiceService.selectCustomerServiceByServiceId(userId);
            nickName = customerService.getName();
        }
        UserInfoResp userInfoResp = new UserInfoResp(userType, nickName, userId);
        System.out.println("用户信息请求的响应:"+userInfoResp.toString());
        Message<UserInfoResp> res = new Message<UserInfoResp>(userInfoResp);
        try {
            session.getBasicRemote().sendText(gson.toJson(res));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
