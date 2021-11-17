package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.exceptions.CafeRuntimeException;
import com.dmbb.cafe.factory.WorkerFactory;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.OrderStatus;
import com.dmbb.cafe.model.dto.OrderDTO;
import com.dmbb.cafe.model.entity.MenuItem;
import com.dmbb.cafe.service.*;
import com.dmbb.cafe.util.MyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.extra.processor.WorkQueueProcessor;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderManagerServiceImpl implements OrderManagerService {

    private Map<String, SimpleWorker> workers = new HashMap<>();

    private WorkQueueProcessor<OrderMealStatus> mealsOrdered = WorkQueueProcessor.create();
    private Queue<OrderMealStatus> mealsWaitingForFood = new PriorityQueue<>();

    private final OrderStatusService orderStatusService;
    private final MenuService menuService;
    private final WorkerFactory workerFactory;
    private final SupplierService supplierService;

    @PostConstruct
    public void init() {
        workers.put("dima", workerFactory.getWorker("dima", this));
        mealsOrdered.subscribe(this::dispatchNewMeal);
        supplierService.subscribeForArrivedFood(this::dispatchMealWaitingForFoodSupply);
    }

    @Override
    public String putOrder(OrderDTO orderDTO) {
        validateOrderMeals(orderDTO.getMeals());
        OrderStatus orderStatus = orderStatusService.createNewOrderStatus(orderDTO);

        orderStatus.getMealStatusMap()
                .values()
                .forEach(mealStatus -> mealsOrdered.onNext(mealStatus));

        return orderStatus.getId();
    }

    @Override
    public void putOrderMealStatusToWaitingForFoodSupply(OrderMealStatus orderMealStatus) {
        mealsWaitingForFood.add(orderMealStatus);
    }

    private void dispatchNewMeal(OrderMealStatus orderMealStatus) {
        for (SimpleWorker worker : workers.values()) {
            if (worker.startNewMealTask(orderMealStatus)) {
                log.info("assigned " + orderMealStatus + " to worker " + worker.getName());
                return;
            }
        }
        log.info("waiting for an idle worker to assign " + orderMealStatus);
        MyUtils.delay(5000);
        dispatchNewMeal(orderMealStatus);
    }

    private void dispatchMealWaitingForFoodSupply(String s) {
        OrderMealStatus orderMealStatusToProceed = mealsWaitingForFood.poll();
        if (orderMealStatusToProceed == null)
            return;

        for (SimpleWorker worker : workers.values()) {
            if (worker.continueMealTask(orderMealStatusToProceed)) {
                log.info("assigned " + orderMealStatusToProceed + " to worker " + worker.getName());
                return;
            }
        }
        log.info("waiting for an idle worker to assign " + orderMealStatusToProceed);
        MyUtils.delay(5000);
        mealsWaitingForFood.add(orderMealStatusToProceed);
        dispatchMealWaitingForFoodSupply(s);
    }

    private void validateOrderMeals(Map<String, Integer> orderMeals) {
        if (CollectionUtils.isEmpty(orderMeals))
            throw new CafeRuntimeException("Order is empty");

        Set<String> existingMenuItems = menuService.getFullMenu()
                .stream()
                .map(MenuItem::getName)
                .collect(Collectors.toSet());
        for (String mealName : orderMeals.keySet()) {
            if (!existingMenuItems.contains(mealName))
                throw new CafeRuntimeException("Meal '" + mealName + "' does not exist");
        }
    }
}
