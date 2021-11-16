package com.dmbb.cafe.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OrderDTO {

    private Map<String, Integer> meals;
    private String customer;

}
