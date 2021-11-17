package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.model.FoodAmountRequiredOrdered;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.kafka.SupplyFoodDTO;
import com.dmbb.cafe.service.FoodService;
import com.dmbb.cafe.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.extra.processor.WorkQueueProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private static final int MULTIPLIER = 5;

    private WorkQueueProcessor<String> foodArrivedSubject = WorkQueueProcessor.create();

    private final FoodService foodService;
    private final KafkaTemplate<String, SupplyFoodDTO> kafkaTemplate;

    @Override
    public synchronized void orderFood(Map<String, Integer> foodAmountRequired, Map<String, Integer> foodAmountShortage) {
        //check if already order
        SupplyFoodDTO supplyFoodDTO = new SupplyFoodDTO();
        Map<String, Integer> foodAmountForSupplyOrder = new HashMap<>();
        foodAmountShortage.forEach((food, number) -> {
            foodAmountForSupplyOrder.put(food, number * MULTIPLIER);
        });
        supplyFoodDTO.setFoodAmount(foodAmountForSupplyOrder);
        kafkaTemplate.send(Constants.KAFKA_TOPIC_SUPPLY_FOOD_ORDER, supplyFoodDTO);
    }

    @KafkaListener(topics = Constants.KAFKA_TOPIC_SUPPLY_FOOD, containerFactory = "kafkaListenerContainerFactorySupplyFood")
    public void foodSupplyListener(SupplyFoodDTO supplyFoodDTO) {
        supplyFoodDTO.getFoodAmount()
                .forEach((food, number) -> foodService.addFoodToStorage(food, number));
        foodArrivedSubject.onNext("s");
        foodArrivedSubject.subscribe();
    }

    /*private Map<String, Integer> getFoodAmountNeedsToOrder(Map<String, Integer> foodAmountRequired, Map<String, Integer> foodAmountShortage) {
        Map<String, Integer> foodAmountNeedsToOrder = new HashMap<>();

        for (Map.Entry<String, Integer> foodShortageEntry : foodAmountShortage.entrySet()) {
            String foodName = foodShortageEntry.getKey();
            FoodAmountRequiredOrdered requiredOrdered = foodAmountRequiredOrderedMap.get(foodName);
            int requiredBefore = requiredOrdered == null ? 0 : requiredOrdered.getRequired();
            int ordered = requiredOrdered == null ? 0 : requiredOrdered.getOrdered();
            int requiredNow = foodAmountRequired.get(foodName);


        }
        return foodAmountNeedsToOrder;
    }*/


    @Override
    public void foodArrived(String foodName, int amount) {

    }

    @Override
    public void subscribeForArrivedFood(Consumer<? super String> consumer) {
        foodArrivedSubject.subscribe(consumer);
    }
}
