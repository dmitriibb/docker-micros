package com.dmbb.springappa.service.impl;

import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;
import com.dmbb.springappa.repository.FoodRepository;
import com.dmbb.springappa.service.FoodService;
import com.dmbb.springappa.service.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RestRequestService restRequestService;

    @Override
    public List<Food> getAllFood() {
        log.info("return all food from FoodService");
        return foodRepository.getAllFood();
    }

    @Override
    public Map<String, Integer> aggregateFoodByColor(List<Food> foodList) {
        log.info("aggregating food by color");
        Map<String, Integer> foodMap = new HashMap<>();
        foodList.forEach(food -> {
            Integer total = foodMap.get(food.getColor());
            total = total == null ? food.getNumber() : total + food.getNumber();
            foodMap.put(food.getColor(), total);
        });

        return foodMap;
    }

    @Override
    public Map<String, Integer> aggregateFoodByName(List<Food> foodList) {
        log.info("aggregating food by name");
        Map<String, Integer> foodMap = new HashMap<>();
        foodList.forEach(food -> {
            Integer total = foodMap.get(food.getName());
            total = total == null ? food.getNumber() : total + food.getNumber();
            foodMap.put(food.getName(), total);
        });

       return foodMap;
    }

    @Override
    public List<String> getCookedFood() {
        log.info("sending food for cooking");
        List<Food> foodList = getAllFood();
        List<String> cookedFood = restRequestService.cookFoodInServiceB(foodList);
        log.info("received cooked food from service b");
        return cookedFood;
    }

    @Override
    public TrayDTO getFoodOnTray() {
        List<String> foodList = getCookedFood();
        return restRequestService.getTrayFromServiceC(foodList);
    }

}
