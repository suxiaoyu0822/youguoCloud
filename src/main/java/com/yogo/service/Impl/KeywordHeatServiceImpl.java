package com.yogo.service.Impl;

import com.yogo.dao.KeywordHeatMapper;
import com.yogo.entity.KeywordAndHeat;
import com.yogo.service.KeywordHeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hp on 2017/7/15.
 */
@Service
public class KeywordHeatServiceImpl implements KeywordHeatService {
    @Autowired
    KeywordHeatMapper keywordHeatMapper;
    public List<KeywordAndHeat> getHeatWord() {
        return keywordHeatMapper.getDayHeat(System.currentTimeMillis());
    }
}
