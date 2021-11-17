package com.dmbb.cafe.kafka.listener;

import com.dmbb.cafe.model.entity.Notification;
import com.dmbb.cafe.repository.NotificationRepository;
import com.dmbb.cafe.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeKafkaListenerImpl {

    private final NotificationRepository notificationRepository;

    //@KafkaListener(topics = Constants.KAFKA_TOPIC_NOTIFICATION, groupId = Constants.KAFKA_GROUP_ID)
    public void listenNotifications(String message) {
        log.info("received message: " + message);
        notificationRepository.save(new Notification(message));
    }

}
