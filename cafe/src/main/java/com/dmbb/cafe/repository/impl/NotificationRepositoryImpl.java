package com.dmbb.cafe.repository.impl;

import com.dmbb.cafe.model.entity.Notification;
import com.dmbb.cafe.repository.NotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    private final List<Notification> data = new ArrayList<>();
    private int nextId;

    @Override
    public List<Notification> getAll() {
        return new ArrayList<>(data);
    }

    @Override
    public synchronized Notification save(Notification notification) {
        Notification forSaving = new Notification(data.size(), notification.getMessage());
        data.add(forSaving);
        return forSaving;
    }
}
