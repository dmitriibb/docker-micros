package com.dmbb.cafe.controller;

import com.dmbb.cafe.model.RestCallSettings;
import com.dmbb.cafe.model.dto.AggregationDTO;
import com.dmbb.cafe.model.dto.FoodOrderDTO;
import com.dmbb.cafe.model.dto.TrayDTO;
import com.dmbb.cafe.model.entity.Food;
import com.dmbb.cafe.service.ConcurrentService;
import com.dmbb.cafe.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@Slf4j
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    private List<Food> getAllFood() {
        log.info("return all food from food controller");
        return foodService.getAllFood();
    }

    @GetMapping("/cook")
    public List<String> cookFood(@RequestParam(required = false) boolean reactive,
                                 @RequestParam(required = false) boolean gateway,
                                 @RequestParam(required = false) boolean loadBalancer) {
        RestCallSettings settings = new RestCallSettings();
        settings.setReactive(reactive);
        settings.setGateway(gateway);
        settings.setLoadBalancer(loadBalancer);
        return foodService.getCookedFood(settings);
    }

    @GetMapping("/cook-on-tray")
    public TrayDTO cookFoodAndPutOnTray(@RequestParam(required = false) boolean reactive,
                                        @RequestParam(required = false) boolean gateway,
                                        @RequestParam(required = false) boolean loadBalancer) {
        RestCallSettings settings = new RestCallSettings();
        settings.setReactive(reactive);
        settings.setGateway(gateway);
        settings.setLoadBalancer(loadBalancer);
        return foodService.getFoodOnTray(settings);
    }

    @GetMapping("/boiled-water")
    public String getBoiledWater(@RequestParam(required = false) boolean reactive,
                                 @RequestParam(required = false) boolean gateway,
                                 @RequestParam(required = false) boolean loadBalancer) {
        RestCallSettings settings = new RestCallSettings();
        settings.setReactive(reactive);
        settings.setGateway(gateway);
        settings.setLoadBalancer(loadBalancer);
        return foodService.getBoiledWater(settings);
    }

    @PutMapping("/cook")
    public List<String> cookMeals(@RequestBody FoodOrderDTO foodOrderDTO,
                                  @RequestParam(required = false) boolean reactive,
                                  @RequestParam(required = false) boolean gateway,
                                  @RequestParam(required = false) boolean loadBalancer) {
        RestCallSettings settings = new RestCallSettings();
        settings.setReactive(reactive);
        settings.setGateway(gateway);
        settings.setLoadBalancer(loadBalancer);
        return foodService.cookMeals(foodOrderDTO, settings);
    }

}
