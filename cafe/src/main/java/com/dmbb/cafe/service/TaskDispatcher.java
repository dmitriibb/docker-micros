package com.dmbb.cafe.service;

import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.entity.MenuItem;

public interface TaskDispatcher {

    void dispatchNewMeal(OrderMealStatus orderMealStatus);

}
