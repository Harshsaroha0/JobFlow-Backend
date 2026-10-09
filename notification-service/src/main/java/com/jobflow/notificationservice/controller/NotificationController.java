package com.jobflow.notificationservice.controller;

import com.jobflow.notificationservice.entity.Notification;
import com.jobflow.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @Valid @RequestBody Notification notification) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.createNotification(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.getNotificationById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByUserId(userId)
        );
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                notificationService.getUnreadNotificationsByUserId(userId)
        );
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                notificationService.markAsRead(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.noContent().build();
    }
}