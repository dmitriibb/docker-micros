package com.dmbb.supplier.model.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FoodSupplyDTO {

    private Map<String, Integer> foodAmount;

}
