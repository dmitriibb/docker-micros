package com.dmbb.cafe.service;

import com.dmbb.cafe.model.OrderMealStatus;

public interface AuditService {

    void newWorkerStatus(SimpleWorker worker, String status);

    void newMealStatus(OrderMealStatus orderMealStatus, String status);

}
