package com.dmbb.cafe.service;

import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.OrderStatus;
import com.dmbb.cafe.model.dto.OrderDTO;
import com.dmbb.cafe.model.dto.OrderResultDTO;
import com.dmbb.cafe.model.dto.OrderStatusDTO;

public interface OrderStatusService {

    void orderMealStatusDone(OrderMealStatus orderMealStatus);

    OrderStatus createNewOrderStatus(OrderDTO orderDTO);

    OrderStatusDTO getOrderStatusById(String orderId);

    OrderStatus getRawOrderStatus(String orderId);

    OrderResultDTO getOrderResult(String orderId);

}
