package com.dmbb.cafe.service;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.exceptions.CafeRuntimeException;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.util.MyUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.extra.processor.WorkQueueProcessor;

import java.util.HashMap;
import java.util.Map;

import static com.dmbb.cafe.constants.Constants.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Slf4j
public class SimpleWorker {

    @EqualsAndHashCode.Include
    private final String name;

    private OrderMealStatus currentMeal;

    private boolean busy;

    private WorkQueueProcessor<OrderMealStatus> newMeals = WorkQueueProcessor.create();

    private WorkQueueProcessor<OrderMealStatus> mealsWaitingForFoodSupply = WorkQueueProcessor.create();

    private final FoodService foodService;
    private final AuditService auditService;
    private final SupplierService supplierService;
    private final OrderStatusService orderStatusService;
    private final OrderManagerService orderManagerService;

    {
        newMeals.subscribe(this::startNewMeal);
        mealsWaitingForFoodSupply.subscribe(this::continueMeal);
    }

    public synchronized boolean startNewMealTask(OrderMealStatus orderMealStatus) {
        if (busy) return false;
        busy();
        newMeals.onNext(orderMealStatus);
        return true;
    }

    private void startNewMeal(OrderMealStatus orderMealStatus) {
        log.info(name + " starting to cook " + orderMealStatus.getMenuItem().getName());
        currentMeal = orderMealStatus;
        addMealStatus(MEAL_STATUS_IN_PROGRESS);
        Map<String, Integer> requiredFood = orderMealStatus.getMenuItem().getIngredients();
        Map<String, Integer> foodFromStorage = foodService.getFoodFromStorage(requiredFood);

        if (foodFromStorage.get(Constants.FOOD_ENOUGH_KEY) == Constants.FOOD_ENOUGH_YES) {
            foodFromStorage.remove(Constants.FOOD_ENOUGH_KEY);
            cookMeal();
        } else {
            foodFromStorage.remove(Constants.FOOD_ENOUGH_KEY);
            orderFood(requiredFood, foodFromStorage);
            orderManagerService.putOrderMealStatusToWaitingForFoodSupply(currentMeal);
            addMealStatus(MEAL_STATUS_WAITING_INGREDIENTS);
            currentMeal = null;
            idle();
        }
    }

    public synchronized boolean continueMealTask(OrderMealStatus orderMealStatus) {
        if (busy) return false;
        busy();
        mealsWaitingForFoodSupply.onNext(orderMealStatus);
        return true;
    }

    private void continueMeal(OrderMealStatus orderMealStatus) {
        log.info(name + " continuing to cook " + orderMealStatus.getMenuItem().getName());
        currentMeal = orderMealStatus;
        addMealStatus(MEAL_STATUS_IN_PROGRESS);
        Map<String, Integer> requiredFood = orderMealStatus.getMenuItem().getIngredients();
        Map<String, Integer> foodFromStorage = foodService.getFoodFromStorage(requiredFood);

        if (foodFromStorage.get(Constants.FOOD_ENOUGH_KEY) == Constants.FOOD_ENOUGH_YES) {
            foodFromStorage.remove(Constants.FOOD_ENOUGH_KEY);
            cookMeal();
        } else {
            orderManagerService.putOrderMealStatusToWaitingForFoodSupply(currentMeal);
            currentMeal = null;
            idle();
        }
    }

    private void orderFood(Map<String, Integer> requiredFood, Map<String, Integer> foodFromStorage) {
        log.info(name + " ordering food");
        Map<String, Integer> foodForOrder = new HashMap<>();
        foodFromStorage.forEach((food, numberNegative) -> foodForOrder.put(food, -numberNegative));
        supplierService.orderFood(requiredFood, foodForOrder);
    }

    private void cookMeal() {
        log.info(name + " cooking " + currentMeal.getMenuItem().getName());
        addMealStatus(MEAL_STATUS_COOKING);
        MyUtils.delay(currentMeal.getMenuItem().getTime());

        log.info(name + " finished cooking " + currentMeal.getMenuItem().getName());

        addMealStatus(MEAL_STATUS_DONE);
        orderStatusService.orderMealStatusDone(currentMeal);
        currentMeal = null;
        idle();
    }

    private void addMealStatus(String status) {
        currentMeal.setStatus(status);
        auditService.newMealStatus(currentMeal, status);
    }

    public synchronized boolean isIdle() {
        return !busy;
    }

    private synchronized void busy() {
        log.info(name + " is busy");
        busy = true;
        auditService.newWorkerStatus(this, "working");
    }

    private synchronized void idle() {
        log.info(name + " is idle");
        auditService.newWorkerStatus(this, "idle");
        busy = false;
    }

}
