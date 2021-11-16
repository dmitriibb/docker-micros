package com.dmbb.cafe.service;

import com.dmbb.cafe.constants.Constants;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.util.MyUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static com.dmbb.cafe.constants.Constants.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class SimpleWorker {

    @EqualsAndHashCode.Include
    private final String name;

    private OrderMealStatus currentMeal;

    private boolean busy;

    private final FoodService foodService;
    private final AuditService auditService;
    private final SupplierService supplierService;
    private final OrderStatusService orderStatusService;

    public void startNewMeal(OrderMealStatus orderMealStatus) {
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
        }

    }

    private void orderFood(Map<String, Integer> requiredFood, Map<String, Integer> foodFromStorage) {
        Map<String, Integer> foodForOrder = new HashMap<>();
        foodFromStorage.forEach((food, numberNegative) ->foodForOrder.put(food, -numberNegative));
        supplierService.orderFood(requiredFood, foodForOrder);
        addMealStatus(MEAL_STATUS_WAITING_INGREDIENTS);
    }

    private void cookMeal() {
        addMealStatus(MEAL_STATUS_COOKING);
        MyUtils.delay(currentMeal.getMenuItem().getTime());
        addMealStatus(MEAL_STATUS_DONE);
    }

    private void addMealStatus(String status) {
        currentMeal.setStatus(status);
        auditService.newMealStatus(currentMeal, status);
    }

    public synchronized boolean isIdle() {
        return !busy;
    }

    private synchronized void busy() {
        busy = true;
        auditService.newWorkerStatus(this, "working");
    }

    private synchronized void idle() {
        auditService.newWorkerStatus(this, "idle");
        busy = false;
    }

}
