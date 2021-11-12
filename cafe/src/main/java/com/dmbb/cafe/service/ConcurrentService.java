package com.dmbb.cafe.service;

import com.dmbb.cafe.model.dto.AggregationDTO;
import com.dmbb.cafe.model.entity.Food;

import java.util.List;

public interface ConcurrentService {

    AggregationDTO aggregateFood(List<Food> foodList);

}
