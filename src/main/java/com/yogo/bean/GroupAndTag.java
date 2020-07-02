package com.yogo.bean;


import com.yogo.entity.GroupWord;
import com.yogo.entity.ServiceGroup;

import java.util.List;

/**
 * Created by hp on 2017/7/30.
 */
public class GroupAndTag {
    public ServiceGroup serviceGroup;
    public List<GroupWord> groupWords;

    public ServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public List<GroupWord> getGroupWords() {
        return groupWords;
    }

    public void setGroupWords(List<GroupWord> groupWords) {
        this.groupWords = groupWords;
    }
}
