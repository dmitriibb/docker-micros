package com.dmbb.cafe.service;

import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.OrderStatus;
import com.dmbb.cafe.model.dto.OrderDTO;

public interface OrderStatusService {

    void orderMealStatusDone(OrderMealStatus orderMealStatus);

    OrderStatus createNewOrderStatus(OrderDTO orderDTO);

}
