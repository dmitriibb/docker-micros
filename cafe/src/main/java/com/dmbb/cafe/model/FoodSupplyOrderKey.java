package com.dmbb.cafe.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class FoodSupplyOrderKey {

    private String orderId;
    private String foodName;

}
