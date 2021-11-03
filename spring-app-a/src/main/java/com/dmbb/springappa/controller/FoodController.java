package com.dmbb.springappa.controller;

import com.dmbb.springappa.model.dto.AggregationDTO;
import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;
import com.dmbb.springappa.service.ConcurrentService;
import com.dmbb.springappa.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
@Slf4j
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final ConcurrentService concurrentService;


    @GetMapping
    private List<Food> getAllFood() {
        log.info("return all food from food controller");
        return foodService.getAllFood();
    }

    @GetMapping("/aggregate")
    public AggregationDTO getFoodAggregated() {
        log.info("get aggregated food from food controller");
        List<Food> foodList = foodService.getAllFood();
        return concurrentService.aggregateFood(foodList);
    }

    @GetMapping("/aggregate-2")
    public AggregationDTO getFoodAggregated2() {
        log.info("get aggregated food from food controller");
        List<Food> foodList = foodService.getAllFood();
        AggregationDTO dto = new AggregationDTO();
        dto.addMetric("name", foodService.aggregateFoodByName(foodList));
        dto.addMetric("color", foodService.aggregateFoodByColor(foodList));
        return dto;
    }

    @GetMapping("/cook")
    public List<String> cookFood() {
        return foodService.getCookedFood();
    }

    @GetMapping("/cook-on-tray")
    public TrayDTO cookFoodAndPutOnTray() {
        return foodService.getFoodOnTray();
    }

}
