package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.exceptions.CafeRuntimeException;
import com.dmbb.cafe.model.dto.AggregationDTO;
import com.dmbb.cafe.model.entity.Food;
import com.dmbb.cafe.service.ConcurrentService;
import com.dmbb.cafe.service.FoodService;
import com.dmbb.cafe.tasks.FoodAggregatorTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Service
public class ConcurrentServiceImpl implements ConcurrentService {

    private final FoodService foodService;
    private final ThreadPoolTaskExecutor executor;

    public ConcurrentServiceImpl(FoodService foodService, @Qualifier("concurrentServiceExecutor") ThreadPoolTaskExecutor executor) {
        this.foodService = foodService;
        this.executor = executor;
    }

    @Override
    public AggregationDTO aggregateFood(List<Food> foodList) {
        FoodAggregatorTask taskColor = new FoodAggregatorTask(foodService, "color", foodList);
        FoodAggregatorTask taskName = new FoodAggregatorTask(foodService, "name", foodList);
        Future<Map<String, Integer>> futureColor = executor.submit(taskColor);
        Future<Map<String, Integer>> futureName = executor.submit(taskName);

        log.info("sent food for aggregation by color and name");

        AggregationDTO aggregationDTO = new AggregationDTO();

        try {
            aggregationDTO.addMetric("color", futureColor.get());
            aggregationDTO.addMetric("name", futureName.get());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CafeRuntimeException(e.getMessage());
        }

        return aggregationDTO;
    }

}
