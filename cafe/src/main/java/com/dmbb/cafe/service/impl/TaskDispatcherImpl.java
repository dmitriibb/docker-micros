package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.factory.WorkerFactory;
import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.service.SimpleWorker;
import com.dmbb.cafe.service.TaskDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.extra.processor.WorkQueueProcessor;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TaskDispatcherImpl implements TaskDispatcher {

    private List<SimpleWorker> workers = new ArrayList<>();

    private WorkQueueProcessor<OrderMealStatus> mealsOrdered = WorkQueueProcessor.create();

    @PostConstruct
    public void init() {
        workers.add(WorkerFactory.getWorker("dima"));
    }

    @Override
    public void dispatchNewMeal(OrderMealStatus orderMealStatus) {
        workers.get(0).startNewMeal(orderMealStatus);
    }
}
