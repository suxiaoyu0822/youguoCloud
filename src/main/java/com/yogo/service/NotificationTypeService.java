package com.yogo.service;


import com.yogo.entity.NotificationType;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface NotificationTypeService {
    int insertNotificationType(String name);

    int deleteNotificationType(int ntId);

    int updateNotificationType(int ntId, String name);

    NotificationType selectNotificationType(int ntId);

    List<NotificationType> selectAllNotificationType();
}
