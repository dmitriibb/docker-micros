package com.dmbb.cafe.repository;

import com.dmbb.cafe.exceptions.NotEnoughFoodInStorageException;
import com.dmbb.cafe.model.entity.Food;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FoodRepository {

    List<Food> getAll();

    Food save(Food food);

    List<Food> getByNameIn(Set<String> names);

    Map<String, Integer> getFoodFromStorage(Map<String, Integer> foodAmount);

    Map<String, Food> getFoodFromStorage(Set<String> names);

    void addFoodToStorage(String foodName, int number);

}
