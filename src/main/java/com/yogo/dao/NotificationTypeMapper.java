package com.yogo.dao;

import com.yogo.entity.NotificationType;
import com.yogo.entity.NotificationTypeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationTypeMapper {
    int updateNotificationType(@Param("ntId") int ntId, @Param("name") String name);

    long countByExample(NotificationTypeExample example);

    int deleteByExample(NotificationTypeExample example);

    int insert(NotificationType record);

    int insertSelective(NotificationType record);

    List<NotificationType> selectByExample(NotificationTypeExample example);

    int updateByExampleSelective(@Param("record") NotificationType record, @Param("example") NotificationTypeExample example);

    int updateByExample(@Param("record") NotificationType record, @Param("example") NotificationTypeExample example);
}