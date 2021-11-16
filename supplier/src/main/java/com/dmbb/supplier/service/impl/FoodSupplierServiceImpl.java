package com.dmbb.supplier.service.impl;

import com.dmbb.supplier.constants.Constants;
import com.dmbb.supplier.model.kafka.FoodSupplyDTO;
import com.dmbb.supplier.service.FoodSupplierService;
import com.dmbb.supplier.util.MyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodSupplierServiceImpl implements FoodSupplierService {

    private final KafkaTemplate<String, FoodSupplyDTO> kafkaTemplate;

    @Override
    @KafkaListener(topics = Constants.KAFKA_TOPIC_SUPPLY_FOOD_ORDER, containerFactory = "foodSupplyKafkaListenerContainerFactory")
    public void processFoodSupplyOrder(FoodSupplyDTO foodSupplyOrder) {
        log.info("received food supply order");

        MyUtils.delay(4000);

        log.info("sending food...");

        FoodSupplyDTO supply = new FoodSupplyDTO();
        supply.setFoodAmount(foodSupplyOrder.getFoodAmount());
        kafkaTemplate.send(Constants.KAFKA_TOPIC_SUPPLY_FOOD, supply);

        log.info("food has been sent");
    }
}
