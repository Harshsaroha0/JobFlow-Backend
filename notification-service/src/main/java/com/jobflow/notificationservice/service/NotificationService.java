package com.jobflow.notificationservice.service;

import com.jobflow.notificationservice.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);

    Notification getNotificationById(Long id);

    List<Notification> getNotificationsByUserId(Long userId);

    List<Notification> getUnreadNotificationsByUserId(Long userId);

    Notification markAsRead(Long id);

    void deleteNotification(Long id);
}