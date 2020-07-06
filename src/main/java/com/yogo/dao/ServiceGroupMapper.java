package com.yogo.dao;

import com.yogo.entity.ServiceGroup;
import com.yogo.entity.ServiceGroupExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceGroupMapper {
    long countByExample(ServiceGroupExample example);

    int deleteByExample(ServiceGroupExample example);

    int insert(ServiceGroup record);

    int insertSelective(ServiceGroup record);

    List<ServiceGroup> selectByExample(ServiceGroupExample example);

    int updateByExampleSelective(@Param("record") ServiceGroup record, @Param("example") ServiceGroupExample example);

    int updateByExample(@Param("record") ServiceGroup record, @Param("example") ServiceGroupExample example);

    int deleteServiceGroup(int groupId);
}