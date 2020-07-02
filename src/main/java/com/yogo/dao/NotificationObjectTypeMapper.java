package com.yogo.dao;

import com.yogo.entity.NotificationObjectType;
import com.yogo.entity.NotificationObjectTypeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationObjectTypeMapper {
    int updateNotificationObjectType(@Param("notId") int notId, @Param("name") String name);

    long countByExample(NotificationObjectTypeExample example);

    int deleteByExample(NotificationObjectTypeExample example);

    int insert(NotificationObjectType record);

    int insertSelective(NotificationObjectType record);

    List<NotificationObjectType> selectByExample(NotificationObjectTypeExample example);

    int updateByExampleSelective(@Param("record") NotificationObjectType record, @Param("example") NotificationObjectTypeExample example);

    int updateByExample(@Param("record") NotificationObjectType record, @Param("example") NotificationObjectTypeExample example);
}