package com.dmbb.springappa.tasks;

import com.dmbb.springappa.model.entity.Food;
import com.dmbb.springappa.service.FoodService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FoodAggregatorTask implements Callable<Map<String, Integer>> {

    private final FoodService foodService;
    private final String aggregateBy;
    private List<Food> foodList;

    public FoodAggregatorTask(FoodService foodService, String aggregateBy, List<Food> foodList) {
        this.foodService = foodService;
        this.aggregateBy = aggregateBy;
        this.foodList = foodList;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        return aggregateBy.equals("color")
                ? foodService.aggregateFoodByColor(foodList)
                : foodService.aggregateFoodByName(foodList);
    }
}
