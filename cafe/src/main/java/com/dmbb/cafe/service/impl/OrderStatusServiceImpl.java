package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.exceptions.CafeRuntimeException;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.OrderStatus;
import com.dmbb.cafe.model.dto.OrderDTO;
import com.dmbb.cafe.model.dto.OrderResultDTO;
import com.dmbb.cafe.model.dto.OrderStatusDTO;
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
        if (isAllMealsDone)
            orderStatus.setStatus(Constants.ORDER_STATUS_READY);
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

    @Override
    public OrderStatusDTO getOrderStatusById(String orderId) {
        OrderStatus orderStatus = orderStatusMap.get(orderId);
        if (orderStatus == null)
            throw new CafeRuntimeException("Order does not exist for id: " + orderId);

        Map<String, Map<String, Integer>> orderMealsMap = new HashMap<>();

        orderStatus.getMealStatusMap()
                .values()
                .forEach(orderMealStatus -> {
                    Map<String, Integer> mealStatuses = orderMealsMap.computeIfAbsent(orderMealStatus.getMenuItem().getName(), k -> new HashMap<>());

                    Integer currentMealStatusNumber = mealStatuses.get(orderMealStatus.getStatus());
                    currentMealStatusNumber = currentMealStatusNumber == null ? 1 : currentMealStatusNumber + 1;
                    mealStatuses.put(orderMealStatus.getStatus(), currentMealStatusNumber);
                });

        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setOrderId(orderId);
        orderStatusDTO.setMeals(orderMealsMap);
        orderStatusDTO.setStatus(orderStatus.getStatus());

        return orderStatusDTO;
    }

    @Override
    public OrderStatus getRawOrderStatus(String orderId) {
        return orderStatusMap.get(orderId);
    }

    @Override
    public OrderResultDTO getOrderResult(String orderId) {
        OrderStatus orderStatus = orderStatusMap.get(orderId);
        if (orderStatus == null)
            throw new CafeRuntimeException("Order does not exist for id: " + orderId);

        if (orderStatus.getStatus().equals(Constants.ORDER_STATUS_DONE))
            throw new CafeRuntimeException("Order has already been given");

        if (!orderStatus.getStatus().equals(Constants.ORDER_STATUS_READY))
            throw new CafeRuntimeException("Order is not ready yet");

        Map<String, Integer> meals = new HashMap<>();
        int cost = orderStatus.getMealStatusMap().values().stream()
                .peek(meal -> {
                    Integer mealNumber = meals.get(meal.getMenuItem().getName());
                    mealNumber = mealNumber == null ? 1 : mealNumber + 1;
                    meals.put(meal.getMenuItem().getName(), mealNumber);
                })
                .map(meal -> meal.getMenuItem().getCost())
                .reduce(0, Integer::sum);

        OrderResultDTO resultDTO = new OrderResultDTO();
        resultDTO.setCost(cost);
        resultDTO.setMeals(meals);

        orderStatus.setStatus(Constants.ORDER_STATUS_DONE);

        return resultDTO;
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
