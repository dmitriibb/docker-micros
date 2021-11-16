package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.model.kafka.SupplyFoodDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, SupplyFoodDTO> supplyFoodOrderKafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, SupplyFoodDTO> supplyFoodOrderKafkaTemplate) {
        this.supplyFoodOrderKafkaTemplate = supplyFoodOrderKafkaTemplate;
    }


    @Override
    public void sendSupplyFoodOrder(SupplyFoodDTO supplyFoodDTO) {
        supplyFoodOrderKafkaTemplate.send(Constants.KAFKA_TOPIC_SUPPLY_FOOD_ORDER, supplyFoodDTO);
    }
}
