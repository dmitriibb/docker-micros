package com.dmbb.cafe.repository;

import com.dmbb.cafe.model.entity.Notification;

import java.util.List;

public interface NotificationRepository {

    List<Notification> getAll();

    Notification save(Notification notification);

}
