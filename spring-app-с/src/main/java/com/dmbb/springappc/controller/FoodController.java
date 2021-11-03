package com.dmbb.springappc.controller;

import com.dmbb.springappc.model.dto.TrayDTO;
import com.dmbb.springappc.service.FoodService;
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

    private final FoodService foodService;

    @PostMapping("/put-on-tray")
    public TrayDTO putFoodOnTray(@RequestBody List<String> foodList) {
        return foodService.putOnTray(foodList);
    }


}
