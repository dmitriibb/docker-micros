package com.dmbb.springappb.service;

import com.dmbb.springappb.model.dto.FoodDTO;

import java.util.List;

public interface FoodCookService {

    String bakeBananas(List<FoodDTO> foodList);

    String brewCoffeeWithMilk(List<FoodDTO> foodList);

    String caramelizeApples(List<FoodDTO> foodList);

}
