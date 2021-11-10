package com.dmbb.springappa.service.impl;

import com.dmbb.springappa.constants.Constants;
import com.dmbb.springappa.model.RestCallSettings;
import com.dmbb.springappa.model.dto.FoodOrderDTO;
import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;
import com.dmbb.springappa.repository.FoodRepository;
import com.dmbb.springappa.service.FoodService;
import com.dmbb.springappa.service.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<String> getCookedFood(RestCallSettings settings) {
        log.info("sending food for cooking");
        List<Food> foodList = getAllFood();
        List<String> cookedFood;
        if (settings.isReactive())
            cookedFood = restRequestService.cookFoodReactive(foodList, settings).block();
        else
            cookedFood = restRequestService.cookFood(foodList, settings);

        log.info("received cooked food from service b");
        return cookedFood;
    }

    @Override
    public TrayDTO getFoodOnTray(RestCallSettings settings) {
        List<String> foodList = getCookedFood(settings);
        return restRequestService.getTrayWithCookedFood(foodList, settings);
    }

    @Override
    public String getBoiledWater(RestCallSettings settings) {
        if (settings.isReactive())
            return restRequestService.getString(Constants.SERVICE_B_NAME, "food/boil-water", settings).block();
        else
            return restRequestService.getString(Constants.SERVICE_B_NAME, "food/boil-water");
    }

    @Override
    public List<String> cookMeals(FoodOrderDTO foodOrderDTO, RestCallSettings settings) {
        long timeStart = System.currentTimeMillis();
        List<String> order;

        if (settings.isReactive())
            order = Flux.fromIterable(foodOrderDTO.getMeals())
                    .flatMap(meal -> restRequestService.getString(Constants.SERVICE_B_NAME, "food/cook/" + meal, settings))
                    .collectList()
                    .block();
        else
            order = foodOrderDTO.getMeals()
                    .stream()
                    .map(meal -> restRequestService.getString(Constants.SERVICE_B_NAME, "food/cook/" + meal))
                    .collect(Collectors.toList());

        String timeTaken = "cooking order took " + (System.currentTimeMillis() - timeStart) + " ms";
        order.add(timeTaken);
        log.info(timeTaken);
        return order;
    }


}
