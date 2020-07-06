package com.yogo.controller;

import com.yogo.bean.ServiceOverview;
import com.yogo.service.ConversationService;
import com.yogo.service.JoinUpService;
import com.yogo.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lee on 2017/7/26.
 */
@Controller
@RequestMapping("/service")
public class ServiceOverviewController {

    @Autowired
    private WorkTimeService workTimeService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private JoinUpService joinUpService;

    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    @ResponseBody
    public ServiceOverview overView(){
        int serviceId = 0;
        ServiceOverview serviceOverView = new ServiceOverview();
        serviceOverView.setAvgScore(conversationService.getAvgScoreByServiceId(serviceId));
        serviceOverView.setAvgScoreRank(conversationService.getAvgScoreRankByServiceId(serviceId));
        serviceOverView.setClientCount(joinUpService.getTodayClientCount());

        return serviceOverView;
    }
}
