package com.dmbb.springappa.service;

import com.dmbb.springappa.model.dto.AggregationDTO;
import com.dmbb.springappa.model.entity.Food;

import java.util.List;

public interface ConcurrentService {

    AggregationDTO aggregateFood(List<Food> foodList);

}
