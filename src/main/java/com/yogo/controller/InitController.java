package com.yogo.controller;

import com.yogo.service.GroupQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Lee on 2017/7/7.
 */
@Controller
@RequestMapping("/")
public class InitController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GroupQueue groupQueue;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("服务器启动！======初始化队列Bean");
        System.out.println(groupQueue);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String kf_login(){
        System.out.println("******************客服登录界面！");
        return "redirect:/kf_login.html";
    }
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public String kh_login(){
        System.out.println("******************客户咨询界面！");
        return "redirect:/customer.html";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String Admin_login(){
        System.out.println("******************管理员界面！");
        return "redirect:/admin_login.html";
    }
}
