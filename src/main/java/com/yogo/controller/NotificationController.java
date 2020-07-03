package com.yogo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.IdList;
import com.yogo.bean.Message;
import com.yogo.bean.NotificationAndTypeAndObjectType;
import com.yogo.bean.Show;
import com.yogo.conversation.ws.ServiceWS;
import com.yogo.conversation.WebSocket;
import com.yogo.entity.CustomerService;
import com.yogo.entity.Notification;
import com.yogo.entity.NotificationObjectType;
import com.yogo.entity.NotificationType;
import com.yogo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 2017/7/19.
 */
@Controller
@RequestMapping(value = "/")
public class NotificationController {
    @Autowired
    private NotificationObjectTypeService notificationObjectTypeService;
    @Autowired
    private NotificationTypeService notificationTypeService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CustomerServiceService customerServiceService;
    @Autowired
    private ServiceGroupService serviceGroupService;


    @RequestMapping("notification")
    @ResponseBody
    public Show notification(){
        Show show = new Show();
        List<NotificationAndTypeAndObjectType>  notificationAndTypeAndObjectTypes = new ArrayList<NotificationAndTypeAndObjectType>();
        List<Notification> notifications = notificationService.selectAllNotification();
        if (notifications!=null){
            for (int i = 0 ; i < notifications.size(); i++){
                NotificationAndTypeAndObjectType notificationAndTypeAndObjectType = new NotificationAndTypeAndObjectType();
                Notification notification = notifications.get(i);
                notificationAndTypeAndObjectType.setNotification(notification);
                NotificationType notificationType = notificationTypeService.selectNotificationType(notification.getNotId());
                notificationAndTypeAndObjectType.setNotificationType(notificationType);
                NotificationObjectType notificationObjectType = notificationObjectTypeService.selectNotificationObjectType(notification.getNotId());
                if (notificationObjectType.getName().equals("客服个人")){
                    notificationAndTypeAndObjectType.setType("客服个人");
                    CustomerService customerService = customerServiceService.selectCustomerServiceByServiceId(notification.getObjectId());
                    notificationAndTypeAndObjectType.setGetMessageObject(customerService);
                }else if(notificationObjectType.getName().equals("客服组")){
                    notificationAndTypeAndObjectType.setType("客服组");
                    notificationAndTypeAndObjectType.setGetMessageObject(serviceGroupService.selectGroupByGroupId(notification.getObjectId()));
                }else{
                    notificationAndTypeAndObjectType.setType("全体客服组");
                    notificationAndTypeAndObjectType.setGetMessageObject(new String("全体客服组"));
                }
                notificationAndTypeAndObjectTypes.add(notificationAndTypeAndObjectType);
            }
        }else{
            show.setStatus(0);
            show.setMessage("没有历史通知");
        }


        show.setData(notificationAndTypeAndObjectTypes);
        return show;
    }

    @RequestMapping("addNotification")
    @ResponseBody
    public Show addNotification(@RequestParam("content") String content, @RequestParam("ntId") int ntId, @RequestParam("objectId")int objectId,
                                @RequestParam("notId") int notId){
        Show show = new Show();
        int res =notificationService.insertNotificationService(ntId,notId,objectId,content);
        if (res == 0){
            show.setStatus(0);
            show.setMessage("添加失败");
        }else{
            show.setMessage("添加成功");
        }
        return  show;
    }

    @RequestMapping("deleteNotification")
    @ResponseBody
    public Show deleteNotification(@RequestParam("deleteList") String getJson){
        Gson gson = new Gson();
        List<IdList> idLists = gson.fromJson(getJson,new TypeToken<List<IdList>>(){}.getType());
        Show show = new Show();
        int fail  = 0 ;
        for (int i = 0 ; i < idLists.size() ; i++){
            int res = notificationService.deleteById(idLists.get(i).getId());
            if (res ==0){
                fail++;
            }
        }
        if (fail !=0){
            show.setStatus(0);
            show.setMessage("删除失败:"+(idLists.size()-fail)+"条");
        }
        return  null;
    }
    @RequestMapping(value = "/sendNotification", method = RequestMethod.POST)
    public void sendNotification(HttpServletRequest req) {
        Gson gson = new Gson();
        int ntId = Integer.parseInt(req.getParameter("ntId"));
        int notId = Integer.parseInt(req.getParameter("notId"));
        int objectId = Integer.parseInt(req.getParameter("objectId"));
        String content = req.getParameter("content");
        NotificationObjectType not = notificationObjectTypeService.selectNotificationObjectType(notId);
        NotificationType nt = notificationTypeService.selectNotificationType(ntId);
        com.yogo.bean.Notification notification = new com.yogo.bean.Notification(nt.getName(), content, new Date().getTime());
        if ("全体客服".equals(not.getName())) {
            for (WebSocket ws : ServiceWS.wsVector) {
                try {
                    ws.getSession().getBasicRemote().sendText(gson.toJson(new Message<com.yogo.bean.Notification>(notification)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if ("客服组".equals(not.getName())) {
            List<CustomerService> list = customerServiceService.selectCustomerServiceByGroup(objectId);
            for (WebSocket ws : ServiceWS.wsVector) {
                for (CustomerService cs : list) {
                    if (ws.getServiceId() == cs.getServiceId()) {
                        try {
                            ws.getSession().getBasicRemote().sendText(gson.toJson(new Message<com.yogo.bean.Notification>(notification)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if ("客服个人".equals(not.getName())) {
            for (WebSocket ws : ServiceWS.wsVector) {
                if (ws.getServiceId() == objectId) {
                    try {
                        ws.getSession().getBasicRemote().sendText(gson.toJson(new Message<com.yogo.bean.Notification>(notification)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //存储入数据库
        notificationService.insertNotificationService(ntId, notId, objectId, content);
    }

    @RequestMapping("searchNot")
    @ResponseBody
    public Show searchGroup(@RequestParam(value = "ntId",defaultValue = "0") int ntId, @RequestParam(value = "notId",defaultValue = "0") int notId){
        Show show = new Show();
        List<NotificationAndTypeAndObjectType>  notificationAndTypeAndObjectTypes = new ArrayList<NotificationAndTypeAndObjectType>();
        List<Notification> notifications = notificationService.searchByType(ntId,notId);
        if (notifications!=null){
            for (int i = 0 ; i < notifications.size(); i++){
                NotificationAndTypeAndObjectType notificationAndTypeAndObjectType = new NotificationAndTypeAndObjectType();
                Notification notification = notifications.get(i);
                notificationAndTypeAndObjectType.setNotification(notification);
                NotificationType notificationType = notificationTypeService.selectNotificationType(notification.getNotId());
                notificationAndTypeAndObjectType.setNotificationType(notificationType);
                NotificationObjectType notificationObjectType = notificationObjectTypeService.selectNotificationObjectType(notification.getNotId());
                if (notificationObjectType.getName().equals("客服个人")){
                    notificationAndTypeAndObjectType.setType("客服个人");
                    CustomerService customerService = customerServiceService.selectCustomerServiceByServiceId(notification.getObjectId());
                    notificationAndTypeAndObjectType.setGetMessageObject(customerService);
                }else if(notificationObjectType.getName().equals("客服组")){
                    notificationAndTypeAndObjectType.setType("客服组");
                    notificationAndTypeAndObjectType.setGetMessageObject(serviceGroupService.selectGroupByGroupId(notification.getObjectId()));
                }else{
                    notificationAndTypeAndObjectType.setType("全体客服组");
                    notificationAndTypeAndObjectType.setGetMessageObject(new String("全体客服组"));
                }
                notificationAndTypeAndObjectTypes.add(notificationAndTypeAndObjectType);
            }
        }else{
            show.setStatus(0);
            show.setMessage("没有历史通知");
        }


        show.setData(notificationAndTypeAndObjectTypes);
        return show;
    }
}
