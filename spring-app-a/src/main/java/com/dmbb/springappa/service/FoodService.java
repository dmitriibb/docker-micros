package com.dmbb.springappa.service;

import com.dmbb.springappa.model.RestCallSettings;
import com.dmbb.springappa.model.dto.FoodOrderDTO;
import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;

import java.util.List;
import java.util.Map;

public interface FoodService {

    List<Food> getAllFood();

    Map<String, Integer> aggregateFoodByColor(List<Food> foodList);

    Map<String, Integer> aggregateFoodByName(List<Food> foodList);

    List<String> getCookedFood(RestCallSettings settings);

    TrayDTO getFoodOnTray(RestCallSettings settings);

    String getBoiledWater(RestCallSettings settings);

    List<String> cookMeals(FoodOrderDTO foodOrderDTO, RestCallSettings settings);

}
