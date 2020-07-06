package com.yogo.service;

import com.yogo.bean.GroupAndPersonNum;
import com.yogo.entity.ServiceGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/7/10.
 */
public interface ServiceGroupService {

    int insertGroup(String name);

    int deleteGroup(int groupId);

    int updateGroup(int groupId, String name);

    ServiceGroup selectGroupByGroupId(int groupId);

    ServiceGroup selectGroupByName(String name);

    List<ServiceGroup> selectAllGroup();
    
    List<GroupAndPersonNum> selectGroupPersonNum();
    
    int deleteGroupById(int id);
}
