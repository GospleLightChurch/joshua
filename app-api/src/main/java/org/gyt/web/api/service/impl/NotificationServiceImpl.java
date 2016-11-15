package org.gyt.web.api.service.impl;

import org.gyt.web.api.repository.NotificationRepository;
import org.gyt.web.api.service.NotificationService;
import org.gyt.web.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> getActivate() {
        return getAll().stream().filter(notification -> notification.getExpireDate().getTime() >= System.currentTimeMillis()).collect(Collectors.toList());
    }

    @Override
    public List<Notification> getExpired() {
        return getAll().stream().filter(notification -> notification.getExpireDate().getTime() < System.currentTimeMillis()).collect(Collectors.toList());
    }

    @Override
    public boolean create(Notification notification) {
        return null != notificationRepository.save(notification);
    }
}
