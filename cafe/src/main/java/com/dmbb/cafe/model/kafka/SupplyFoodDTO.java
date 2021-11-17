package com.dmbb.cafe.model.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SupplyFoodDTO {

    private Map<String, Integer> foodAmount;

}
