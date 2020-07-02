package com.yogo.controller;

import com.yogo.entity.CustomerService;
import com.yogo.service.CustomerServiceService;
import com.yogo.service.OnlineService;
import com.yogo.service.ServiceGroupPeople;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Lee on 2017/7/17.
 */
@Controller
@RequestMapping(value ="/" )
public class ServiceLoginController {

    @Autowired
    private CustomerServiceService customerServiceService;

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private ServiceGroupPeople serviceGroupPeople;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, HttpServletResponse resp){
        String employeeId = req.getParameter("employeeId");
        System.out.println("用户："+employeeId+"开始登陆了！");
        String password = req.getParameter("password");
        if (employeeId != null && password != null){
            CustomerService customerService = customerServiceService.selectByEIdAndPwd(employeeId,password);
            if (customerService != null){
                onlineService.online(employeeId, resp);
                serviceGroupPeople.joinGroup(customerService.getGroupId(), customerService.getServiceId());
                return "redirect:/customerService.html";
            }
        }
        return null;
    }

}
