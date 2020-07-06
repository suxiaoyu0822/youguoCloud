package com.yogo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yogo.bean.GroupAndTag;
import com.yogo.bean.IdList;
import com.yogo.bean.PeopleDayAndTime;
import com.yogo.bean.Show;
import com.yogo.entity.ChatLogAndSendRecInfo;
import com.yogo.entity.Client;
import com.yogo.entity.CustomerService;
import com.yogo.entity.ServiceGroup;
import com.yogo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cjn on 2017/7/14.
 * 客服人员管理控制类
 */
@Controller
public class CustomerServiceManagementController {
    @Autowired
    ServiceGroupService serviceGroupService;
    @Autowired
    CustomerServiceService customerServiceService;
    @Autowired
    ConversationService conversationService;
    @Autowired
    GroupWordService groupWordService;
    @Autowired
    ChatLogService chatLogService;

    /**
     * Create By Cjn
     * 获取公司中所有的部门
     *
     * @param
     * @return
     */
    @RequestMapping(value = "customerServiceManagement")
    @ResponseBody
    public Show management() {
        Show show = new Show();
        List<ServiceGroup> serviceGroups = serviceGroupService.selectAllGroup();
        List<GroupAndTag> list = new ArrayList<GroupAndTag>();
        for (int i = 0; i < serviceGroups.size(); i++) {
            GroupAndTag groupAndTag = new GroupAndTag();
            groupAndTag.setServiceGroup(serviceGroups.get(i));
            groupAndTag.setGroupWords(groupWordService.selectGroupTag(serviceGroups.get(i).getGroupId()));
            list.add(groupAndTag);
        }
        show.setData(list);
        return show;
    }

    @RequestMapping(value = "allCustom")
    @ResponseBody
    public Show person() {
        Show show = new Show();
        show.setData(customerServiceService.selectNotDimissionPerson());
        return show;
    }


    @RequestMapping(value = "selectByGroup")
    @ResponseBody
    public Show selectByGroup(@RequestParam("groupId") int groupId) {
        Show show = new Show();
        show.setData(customerServiceService.selectCustomerServiceByGroup(groupId));
        return show;
    }

    /**
     * Create By Cjn
     * 获取某个部门中所有的成员
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "searchGroupPerson", method = RequestMethod.GET)
    @ResponseBody
    public Show searchGroupPerson(@RequestParam("groupId") int groupId) {
        List<CustomerService> member = customerServiceService.selectCustomerServiceByGroup(groupId);//调用CW
        Show show = new Show();
        show.setData(member);
        show.setStatus(member.size());
        show.setMessage("该部门共有:" + member.size() + "人");
        return show;
    }

    /**
     * Create By Cjn
     * 用于批量离职客服人员
     *
     * @param
     * @return
     */
    @RequestMapping(value = "deleteServerPerson")
    @ResponseBody
    public Show deletePerson(@RequestParam("deleteGroup") String getJson) {
        System.out.println(getJson);
        Gson gson = new Gson();
        List<IdList> customId = gson.fromJson(getJson, new TypeToken<List<IdList>>() {
        }.getType());
        Show show = new Show();
        int fail = 0;
        for (int i = 0; i < customId.size(); i++) {
            System.out.println("!!!" + customId.get(i).getId());
            int res = customerServiceService.updateCustomDimission(customId.get(i).getId());
            if (res == 0) {
                fail++;
            }
        }
        if (fail != 0) {
            show.setStatus(0);
            show.setMessage("删除失败:" + (customId.size() - fail) + "条");
        }
        return show;
    }

    /**
     * Create By cjn
     * 批量添加客服人员
     *
     * @param
     * @return show
     */
    @RequestMapping(value = "addServerPerson")
    @ResponseBody
    public Show addServerPerson(@RequestParam("personList") String getJson) {
        Gson gson = new Gson();
        List<CustomerService> customerServices = gson.fromJson(getJson, new TypeToken<List<CustomerService>>() {
        }.getType());
        int res = 0;
        for (int i = 0; i < customerServices.size(); i++) {
            System.out.println(customerServices.get(i));
            int re = customerServiceService.insertCustomerService(customerServices.get(i).getName(), customerServices.get(i).getGroupId(), customerServices.get(i).getNickname(),
                    customerServices.get(i).getEmployeeId(), customerServices.get(i).getAutoMessage());
            if (re == 1) {
                res++;
            }
        }
        Show show = new Show();
        if (res == customerServices.size()) {
            show.setStatus(1);
        } else {
            show.setStatus(0);
            show.setMessage("请求插入" + customerServices.size() + "条," + "成功插入" + res + "条。");
        }
        return show;
    }

    /**
     * 查询客服和客服所对应的绩效
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "searchPerson")
    @ResponseBody
    public Show searchPersonAndPerformance(@RequestParam("name") String name) {
        Show show = new Show();
        List<CustomerService> customerService = customerServiceService.selectBySearchName(name);
        if (customerService.size() == 0) {
            show.setStatus(0);
            show.setMessage("无此客服人员");
        } else {
            List<PeopleDayAndTime> res = new ArrayList<PeopleDayAndTime>();
            for (int i = 0; i < customerService.size(); i++) {
                PeopleDayAndTime peopleDayAndTime = new PeopleDayAndTime();
                CustomerService cs = customerService.get(i);
                System.out.println("ServiceId:" + cs.getServiceId());
                peopleDayAndTime.setCustomerService(cs);
                peopleDayAndTime.setMonthInfo(conversationService.selectRecentPeopleMonth(cs.getServiceId()));
                peopleDayAndTime.setWeekInfo(conversationService.selectRecentPeopleWeekend(cs.getServiceId()));
                peopleDayAndTime.setDayInfo(conversationService.selectRecentPeopleHour(cs.getServiceId()));
                res.add(peopleDayAndTime);
            }
            show.setData(res);
        }
        return show;
    }

    @RequestMapping("updateService")
    @ResponseBody
    public Show updateService(@RequestParam("personList") String personList) {
        Show show = new Show();
        Gson gson = new Gson();
        System.out.println(personList);
        List<CustomerService> customerServices = gson.fromJson(personList, new TypeToken<List<CustomerService>>() {
        }.getType());
        System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
        int res = 0;
        for (int i = 0; i < customerServices.size(); i++) {
            int re = customerServiceService.updateCustomerService(customerServices.get(i).getServiceId(), customerServices.get(i).getName(), customerServices.get(i).getGroupId(), customerServices.get(i).getNickname(),
                    customerServices.get(i).getEmployeeId(), customerServices.get(i).getAutoMessage());
            if (re == 1) {
                res++;
            }
        }
        if (res == customerServices.size()) {
            show.setStatus(1);
        } else {
            show.setStatus(0);
            show.setMessage("请求更新" + customerServices.size() + "条," + "成功更新" + res + "条。");
        }
        return show;
    }

    @RequestMapping("clientChatList")
    @ResponseBody
    public Show clientChatList(@RequestParam("serviceId") int serviceId) {
        Show show = new Show();
        List<Client> clients = conversationService.selectClientChatList(serviceId);
        if (clients == null || clients.size() == 0) {
            show.setStatus(0);
            show.setMessage("没有对话用户");
        } else {
            show.setData(clients);
        }
        return show;
    }

    @RequestMapping("clientAndServerChatLog")
    @ResponseBody
    public Show clientAndServerChatLog(@RequestParam("serviceId") int serviceId, @RequestParam("clientId") int clientId) {
        Show show = new Show();
        List<ChatLogAndSendRecInfo> chatLogAndSendRecInfos = chatLogService.selectClientAndServerChatLog(clientId, serviceId);
        if (chatLogAndSendRecInfos == null || chatLogAndSendRecInfos.size() == 0) {
            show.setStatus(0);
            show.setMessage("没有对话用户");
        } else {
            show.setData(chatLogAndSendRecInfos);
        }
        return show;
    }

    @RequestMapping("clientChatLog")
    @ResponseBody
    public Show clientAndServerChatLog(@RequestParam("clientId") int clientId) {
        Show show = new Show();
        List<ChatLogAndSendRecInfo> chatLogAndSendRecInfos = chatLogService.selectClientChatLog(clientId);
        if (chatLogAndSendRecInfos == null || chatLogAndSendRecInfos.size() == 0) {
            show.setStatus(0);
            show.setMessage("没有对话用户");
        } else {
            show.setData(chatLogAndSendRecInfos);
        }
        return show;
    }
}
