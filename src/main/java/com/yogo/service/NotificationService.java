package com.yogo.service;


import com.yogo.entity.Notification;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public interface NotificationService {

    int insertNotificationService(int ntId, int notId, int objectId, String content);

    List<Notification> selectAllNotification();

    int deleteById(int id);

    List<Notification> searchByType(int ntId, int notId);
}
