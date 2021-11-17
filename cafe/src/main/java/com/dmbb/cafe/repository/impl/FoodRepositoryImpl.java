package com.dmbb.cafe.repository.impl;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.exceptions.NotEnoughFoodInStorageException;
import com.dmbb.cafe.model.entity.Food;
import com.dmbb.cafe.repository.FoodRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FoodRepositoryImpl extends SimpleInMemoryRepository<Food> implements FoodRepository{

    @Override
    public List<Food> getByNameIn(Set<String> names) {
        return getAll().stream()
                .filter(food -> names.contains(food.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized Map<String, Integer> getFoodFromStorage(Map<String, Integer> foodAmount){
        Map<String, Integer> foodAmountToReturn = new HashMap<>();
        Map<String, Integer> foodAmountToStorage = new HashMap<>();

        Map<String, Food> foodMap = getFoodFromStorage(foodAmount.keySet());
        boolean isShortage = false;
        //check for required amount
        for (Map.Entry<String, Integer> entry: foodAmount.entrySet()) {
            Food foodInStorage = foodMap.get(entry.getKey());
            Integer currentFoodAmount = foodInStorage == null ? 0 : foodInStorage.getNumber();
            int foodWillRemain = currentFoodAmount - entry.getValue();
            if (foodWillRemain < 0)
                isShortage = true;
            foodAmountToReturn.put(entry.getKey(), entry.getValue());
            foodAmountToStorage.put(entry.getKey(), foodWillRemain);

        }

        if (!isShortage) {
            foodMap.forEach((name, food) -> {
                food.setNumber(foodAmountToStorage.get(name));
                save(food);
            });
            foodAmountToReturn.put(Constants.FOOD_ENOUGH_KEY, Constants.FOOD_ENOUGH_YES);
        } else {
            foodAmountToReturn = foodAmountToStorage.entrySet().stream()
                    .filter(entry -> entry.getValue() < 0)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            foodAmountToReturn.put(Constants.FOOD_ENOUGH_KEY, Constants.FOOD_ENOUGH_NO);
        }

        return foodAmountToReturn;
    }

    @Override
    public Map<String, Food> getFoodFromStorage(Set<String> names) {
        List<Food> foodList = getByNameIn(names);
        Map<String, Food> map = new HashMap<>();

        for (Food food : foodList) {
            map.put(food.getName(), food);
        }

        return map;
    }

    @Override
    public synchronized void addFoodToStorage(String foodName, int number) {
        Food food = getByName(foodName);

        if (food == null) {
            food = new Food();
            food.setName(foodName);
            food.setNumber(0);
        }

        food.setNumber(food.getNumber() + number);
        save(food);
    }

}
