package com.dmbb.springappb.service.impl;

import com.dmbb.springappb.model.dto.FoodDTO;
import com.dmbb.springappb.service.FoodCookService;
import com.dmbb.springappb.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FoodCookServiceImpl implements FoodCookService {

    @Value("${response.delay}")
    private long responseDelay;

    @Value("${instance.name}")
    private String instanceName;

    @Override
    public String bakeBananas(List<FoodDTO> foodList) {
        log.info("baking bananas");
        MyUtils.delay();

        int totalBananas = foodList.stream()
                .filter(food -> "banana".equals(food.getName()))
                .map(FoodDTO::getNumber)
                .reduce(0, Integer::sum);

        return "baked bananas: " + totalBananas;
    }

    @Override
    public String brewCoffeeWithMilk(List<FoodDTO> foodList) {
        log.info("brewing coffee with milk");
        MyUtils.delay();

        Map<String, Integer> map = foodList.stream()
                .collect(Collectors.groupingBy(FoodDTO::getName, Collectors.summingInt(FoodDTO::getNumber)));

        Integer coffee = map.get("coffee");
        if (coffee == null || coffee == 0)
            return "no coffee provided";

        Integer milk = map.get("milk");
        if (milk == null || milk == 0)
            return "brewed coffee without milk: " + coffee;

        return "brewed coffee with milk: " + Math.min(coffee, milk);

    }

    @Override
    public String caramelizeApples(List<FoodDTO> foodList) {
        log.info("caramelizing apples");
        MyUtils.delay();

        Map<String, Integer> map = foodList.stream()
                .collect(Collectors.groupingBy(FoodDTO::getName, Collectors.summingInt(FoodDTO::getNumber)));

        Integer apples = map.get("apple");
        if (apples == null || apples == 0)
            return "no apples provided";

        Integer sugar = map.get("sugar");
        if (sugar == null || sugar == 0)
            return "no sugar provided";

        return "caramelized apples: " + Math.min(apples, sugar);
    }

    @Override
    public String boilWater() {
        long timeStart = System.currentTimeMillis();
        MyUtils.delay(responseDelay);
        log.info("boiling water took: " + (System.currentTimeMillis() - timeStart) + "ms");

        return "boiled water from '" + instanceName + "'";
    }

    @Override
    public String cookMeal(String meal) {
        log.info("cooking " + meal);

        switch (meal) {
            case "beef":
                MyUtils.delay(2000);
                return "grilled beef";
            case "chicken":
                MyUtils.delay(1000);
                return "fried chicken";
            case "eggs":
                MyUtils.delay(1500);
                return "fried eggs";
            case "coffee":
                MyUtils.delay(2000);
                return "sweet coffee with milk";
            default:
                MyUtils.delay(500);
                return "there is no recipe for " + meal;
        }
    }
}
