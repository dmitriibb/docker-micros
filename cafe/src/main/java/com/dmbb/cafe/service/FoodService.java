package com.dmbb.cafe.service;

import com.dmbb.cafe.exceptions.NotEnoughFoodInStorageException;
import com.dmbb.cafe.model.RestCallSettings;
import com.dmbb.cafe.model.dto.FoodOrderDTO;
import com.dmbb.cafe.model.dto.TrayDTO;
import com.dmbb.cafe.model.entity.Food;

import java.util.List;
import java.util.Map;

public interface FoodService {

    List<Food> getAllFood();

    Map<String, Integer> getFoodFromStorage(Map<String, Integer> foodAmountMap);

    void addFoodToStorage(String foodName, int number);

    List<String> getCookedFood(RestCallSettings settings);

    TrayDTO getFoodOnTray(RestCallSettings settings);

    String getBoiledWater(RestCallSettings settings);

    List<String> cookMeals(FoodOrderDTO foodOrderDTO, RestCallSettings settings);

}
