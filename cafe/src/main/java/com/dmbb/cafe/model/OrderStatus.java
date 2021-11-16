package com.dmbb.cafe.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderStatus {

    private String id;

    private String status;

    Map<String, OrderMealStatus> mealStatusMap;

}
