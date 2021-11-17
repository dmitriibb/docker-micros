package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.model.entity.Notification;
import com.dmbb.cafe.repository.NotificationRepository;
import com.dmbb.cafe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAll() {
        return notificationRepository.getAll();
    }
}
