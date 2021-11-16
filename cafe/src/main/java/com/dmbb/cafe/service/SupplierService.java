package com.dmbb.cafe.service;

import java.util.Map;
import java.util.function.Consumer;

public interface SupplierService {

    void orderFood(Map<String, Integer> foodAmountRequired, Map<String, Integer> foodAmountShortage);

    void foodArrived(String foodName, int amount);

    void subscribeForArrivedFood(Consumer<? super String> consumer);

}
