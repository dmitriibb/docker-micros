package com.dmbb.cafe.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OrderStatusDTO {

    private String orderId;

    private String status;

    private Map<String, Map<String, Integer>> meals;

}
