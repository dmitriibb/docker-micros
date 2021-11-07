package com.dmbb.springappa.service;

import com.dmbb.springappa.model.dto.FoodOrderDTO;
import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;

import java.util.List;
import java.util.Map;

public interface FoodService {

    List<Food> getAllFood();

    Map<String, Integer> aggregateFoodByColor(List<Food> foodList);

    Map<String, Integer> aggregateFoodByName(List<Food> foodList);

    List<String> getCookedFood();

    TrayDTO getFoodOnTray();

    String getBoiledWater(boolean reactive);

    List<String> cookMeals(FoodOrderDTO foodOrderDTO, boolean reactive);

}
