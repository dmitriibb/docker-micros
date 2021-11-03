package com.dmbb.springappb.controller;

import com.dmbb.springappb.model.dto.FoodListDTO;
import com.dmbb.springappb.service.FoodCookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@Slf4j
public class FoodController {

    private final FoodCookService foodCookService;

    @PostMapping("/cook")
    public List<String> cookFood(@RequestBody FoodListDTO foodListDTO) {
        log.info("cook food request received");

        String bananas = foodCookService.bakeBananas(foodListDTO.getFoodList());
        String coffee = foodCookService.brewCoffeeWithMilk(foodListDTO.getFoodList());
        String apples = foodCookService.caramelizeApples(foodListDTO.getFoodList());

        log.info("food has been cooked");
        return Arrays.asList(bananas, coffee, apples);
    }


}
