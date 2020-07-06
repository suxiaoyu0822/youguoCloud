package com.yogo.controller;

import com.yogo.entity.KeywordAndHeat;
import com.yogo.service.KeywordHeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by hp on 2017/7/15.
 */
@Controller
public class TestCjnController {
    @Autowired
    KeywordHeatService keywordHeatService;
    
    @RequestMapping("x5")
    @ResponseBody
    public List<KeywordAndHeat> xxx(){
        System.out.println(keywordHeatService.getHeatWord().get(0).getKeywords().get(0).getValue());
        return keywordHeatService.getHeatWord();
    }
}
