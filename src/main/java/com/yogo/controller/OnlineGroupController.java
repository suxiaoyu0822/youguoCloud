package com.yogo.controller;

import com.yogo.entity.CustomerService;
import com.yogo.entity.ServiceGroup;
import com.yogo.service.CustomerServiceService;
import com.yogo.service.OnlineService;
import com.yogo.service.ServiceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Lee on 2017/7/24.
 */
@Controller
@RequestMapping(value = "/api")
public class OnlineGroupController {

    @Autowired
    private ServiceGroupService serviceGroupService;

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private CustomerServiceService customerServiceService;

    @RequestMapping(value = "/onlineGroup", method = RequestMethod.GET)
    @ResponseBody
    public List<ServiceGroup> onlineGroup(){
        List<ServiceGroup> list = serviceGroupService.selectAllGroup();
        for (String employeeId : onlineService.getMap().values()){
            CustomerService customerService = customerServiceService.selectCustomerServiceByEmployeeId(employeeId);
            for (ServiceGroup serviceGroup : list){
                if (serviceGroup.getGroupId().equals(customerService.getGroupId())){
                    serviceGroup.getOnlineMembers().add(customerService);
                }
            }
        }
        return list;
    }
}
