package com.dmbb.kitchen.service;

import com.dmbb.kitchen.model.dto.FoodDTO;

import java.util.List;

public interface FoodCookService {

    String bakeBananas(List<FoodDTO> foodList);

    String brewCoffeeWithMilk(List<FoodDTO> foodList);

    String caramelizeApples(List<FoodDTO> foodList);

    String boilWater();

    String cookMeal(String meal);

}
