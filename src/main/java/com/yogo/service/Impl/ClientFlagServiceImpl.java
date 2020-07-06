package com.yogo.service.Impl;

import com.yogo.dao.ClientFlagMapper;
import com.yogo.entity.ClientFlag;
import com.yogo.service.ClientFlagService;
import com.yogo.service.FlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/7/11.
 */
@Service
public class ClientFlagServiceImpl implements ClientFlagService {

    @Autowired
    private ClientFlagMapper clientFlagMapper;

    @Autowired
    private FlagService flagService;

    @Transactional
    public int insertClientFlag(int clientId, String name) {
        int selectSum = flagService.selectFlagId(name);
        if(selectSum == 0){
            flagService.insertFlag(name);
        }
        int flagId = flagService.selectFlagId(name);
        ClientFlag clientFlag = new ClientFlag();
        clientFlag.setClientId(clientId);
        clientFlag.setFlagId(flagId);
        int insertSum = clientFlagMapper.insertSelective(clientFlag);
        return insertSum;
    }

    @Transactional
    public int deleteClientFlag(int clientId,int flagId){
        int deleteSum = clientFlagMapper.deleteClientFlag(clientId,flagId);
        return deleteSum;
    }
}
