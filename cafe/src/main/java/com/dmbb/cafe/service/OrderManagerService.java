package com.dmbb.cafe.service;

import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.dto.OrderDTO;

public interface OrderManagerService {

    String putOrder(OrderDTO orderDTO);

    void putOrderMealStatusToWaitingForFoodSupply(OrderMealStatus orderMealStatus);

}
