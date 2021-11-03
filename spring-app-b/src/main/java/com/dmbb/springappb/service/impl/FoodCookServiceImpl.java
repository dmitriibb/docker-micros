package com.dmbb.springappb.service.impl;

import com.dmbb.springappb.model.dto.FoodDTO;
import com.dmbb.springappb.service.FoodCookService;
import com.dmbb.springappb.util.MyUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FoodCookServiceImpl implements FoodCookService {

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
}
