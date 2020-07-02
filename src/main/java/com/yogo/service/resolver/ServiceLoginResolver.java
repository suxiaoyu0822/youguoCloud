package com.yogo.service.resolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.*;
import com.yogo.conversation.WebSocket;
import com.yogo.entity.CommonLanguage;
import com.yogo.entity.CustomerService;
import com.yogo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Lee on 2017/7/17.
 */
@Service
public class ServiceLoginResolver implements ContentResolver {

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private ServiceGroupService serviceGroupService;

    @Autowired
    private GroupQueue groupQueue;

    @Autowired
    private WorkTimeService workTimeService;

    @Autowired
    private CommonLanguageService commonLanguageService;

    public void resolve(String msgJson, WebSocket webSocket) {
        System.out.println("++++++++++++++++客服会话");
        Gson gson = new Gson();
        Session session = webSocket.getSession();
        Type objectType = new TypeToken<Message<ServiceLogin>>(){}.getType();
        Message<ServiceLogin> message = gson.fromJson(msgJson, objectType);
        System.out.println("____________________"+message.getContent().toString());
        ServiceLogin serviceLogin = message.getContent();
        String token = serviceLogin.getToken();
        if (token.startsWith("\"")){
            token = token.substring(1,token.length() - 1);
        }
        System.out.println("1!!!" +token);
        String employeeId = onlineService.getEmployeeId(token);
        System.out.println("2!!!" + employeeId);
        CustomerService customerService = customerServiceService.selectCustomerServiceByEmployeeId(employeeId);
        System.out.println("3!!!" + customerService);
        webSocket.setServiceId(customerService.getServiceId());
        String groupName = serviceGroupService.selectGroupByGroupId(customerService.getGroupId()).getName();
        System.out.println("4!!!" + groupName);
        ServiceInfo serviceInfo = new ServiceInfo(customerService.getServiceId(),customerService.getName(),customerService.getNickname(), groupName ,customerService.getEmployeeId(),customerService.getAutoMessage());
        Message<ServiceInfo> res = new Message<ServiceInfo>(serviceInfo);
        int queuePeopleCount = groupQueue.getGroupQueueMap().get(customerService.getGroupId()).size();
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setQueuePeopleCount(queuePeopleCount);

        //常用语列表
        List<CommonLanguage> list = commonLanguageService.selectAllServiceAndCommonLanguage(webSocket.getServiceId());
        CommonLanguageList commonLanguageList = new CommonLanguageList();
        if(list != null && list.size() > 0){
            commonLanguageList.setCommonLanguageList(list);
        }
        Message<CommonLanguageList> commonLanguageListRes = new Message<CommonLanguageList>(commonLanguageList);

        try {
            session.getBasicRemote().sendText(gson.toJson(commonLanguageListRes));
            session.getBasicRemote().sendText(gson.toJson(res));
            session.getBasicRemote().sendText(gson.toJson(new Message<ServiceStatus>(serviceStatus)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //记录上线时间
        workTimeService.online(customerService.getServiceId());
    }
}
