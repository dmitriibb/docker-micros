package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.OrderStatus;
import com.dmbb.cafe.model.dto.OrderDTO;
import com.dmbb.cafe.model.entity.MenuItem;
import com.dmbb.cafe.service.MenuService;
import com.dmbb.cafe.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.dmbb.cafe.constants.Constants.MEAL_STATUS_RECEIVED;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStatusServiceImpl implements OrderStatusService {

    private final MenuService menuService;

    private Map<String, OrderStatus> orderStatusMap = new HashMap<>();


    @Override
    public void orderMealStatusDone(OrderMealStatus orderMealStatus) {
        OrderStatus orderStatus = orderStatusMap.get(orderMealStatus.getOrderId());
        boolean isAllMealsDone = orderStatus.getMealStatusMap().values()
                .stream()
                .allMatch(mealStatus -> mealStatus.getStatus().equals(Constants.MEAL_STATUS_DONE));
    }

    @Override
    public OrderStatus createNewOrderStatus(OrderDTO orderDTO) {
        String orderId = UUID.randomUUID().toString();
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(orderId);
        orderStatus.setStatus(Constants.ORDER_STATUS_RECEIVED);

        Map<String, OrderMealStatus> orderMealStatusMap = new HashMap<>();
        orderDTO.getMeals().forEach((mealName, number) -> {
            for (int i = 0; i < number; i++) {
                OrderMealStatus orderMealStatus = createOrderMealStatus(mealName, orderId);
                orderMealStatusMap.put(orderMealStatus.getId(), orderMealStatus);
            }
        });

        orderStatus.setMealStatusMap(orderMealStatusMap);
        orderStatusMap.put(orderId, orderStatus);
        return orderStatus;
    }

    private OrderMealStatus createOrderMealStatus(String mealName, String orderId) {
        MenuItem menuItem = menuService.getMenuItemByName(mealName);
        String mealId = UUID.randomUUID().toString();
        OrderMealStatus orderMealStatus = new OrderMealStatus();
        orderMealStatus.setId(mealId);
        orderMealStatus.setOrderId(orderId);
        orderMealStatus.setMenuItem(menuItem);
        orderMealStatus.setStatus(MEAL_STATUS_RECEIVED);
        return orderMealStatus;
    }
}
