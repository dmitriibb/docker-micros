package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.exceptions.NotEnoughFoodInStorageException;
import com.dmbb.cafe.model.RestCallSettings;
import com.dmbb.cafe.model.dto.FoodOrderDTO;
import com.dmbb.cafe.model.dto.TrayDTO;
import com.dmbb.cafe.model.entity.Food;
import com.dmbb.cafe.repository.FoodRepository;
import com.dmbb.cafe.service.FoodService;
import com.dmbb.cafe.service.RestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final RestRequestService restRequestService;

    @Override
    public List<Food> getAllFood() {
        log.info("return all food from FoodService");
        return foodRepository.getAll();
    }

    @Override
    public Map<String, Integer> getFoodFromStorage(Map<String, Integer> foodAmountMap){
        return foodRepository.getFoodFromStorage(foodAmountMap);
    }

    @Override
    public void addFoodToStorage(String foodName, int number) {
        foodRepository.addFoodToStorage(foodName, number);
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
            return restRequestService.getString(Constants.SERVICE_NAME_KITCHEN, "food/boil-water", settings).block();
        else
            return restRequestService.getString(Constants.SERVICE_NAME_KITCHEN, "food/boil-water");
    }

    @Override
    public List<String> cookMeals(FoodOrderDTO foodOrderDTO, RestCallSettings settings) {
        long timeStart = System.currentTimeMillis();
        List<String> order;

        if (settings.isReactive())
            order = Flux.fromIterable(foodOrderDTO.getMeals())
                    .flatMap(meal -> restRequestService.getString(Constants.SERVICE_NAME_KITCHEN, "food/cook/" + meal, settings))
                    .collectList()
                    .block();
        else
            order = foodOrderDTO.getMeals()
                    .stream()
                    .map(meal -> restRequestService.getString(Constants.SERVICE_NAME_KITCHEN, "food/cook/" + meal))
                    .collect(Collectors.toList());

        String timeTaken = "cooking order took " + (System.currentTimeMillis() - timeStart) + " ms";
        order.add(timeTaken);
        log.info(timeTaken);
        return order;
    }


}
