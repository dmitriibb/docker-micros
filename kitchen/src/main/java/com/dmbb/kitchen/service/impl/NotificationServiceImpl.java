package com.dmbb.kitchen.service.impl;

import com.dmbb.kitchen.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.dmbb.kitchen.constants.Constants.KAFKA_TOPIC_NOTIFICATION;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendNotification(String notification) {
        kafkaTemplate.send(KAFKA_TOPIC_NOTIFICATION, notification);
    }
}
