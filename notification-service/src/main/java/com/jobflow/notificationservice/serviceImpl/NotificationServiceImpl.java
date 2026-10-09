package com.jobflow.notificationservice.serviceImpl;

import com.jobflow.notificationservice.entity.Notification;
import com.jobflow.notificationservice.exception.NotificationNotFoundException;
import com.jobflow.notificationservice.repository.NotificationRepository;
import com.jobflow.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification) {
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new NotificationNotFoundException(
                                "Notification not found with id: " + id
                        ));
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }

    @Override
    public Notification markAsRead(Long id) {
        Notification notification = getNotificationById(id);

        notification.setRead(true);

        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);

        notificationRepository.delete(notification);
    }
}