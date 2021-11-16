package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.model.OrderMealStatus;
import com.dmbb.cafe.model.entity.AuditEvent;
import com.dmbb.cafe.model.enums.AuditEventType;
import com.dmbb.cafe.service.AuditService;
import com.dmbb.cafe.service.SimpleWorker;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class AuditServiceImpl implements AuditService {

    private List<AuditEvent> data = new ArrayList<>();

    @Override
    public void newWorkerStatus(SimpleWorker worker, String status) {
        AuditEvent event = new AuditEvent();
        event.setWorkerName(worker.getName());
        event.setType(AuditEventType.WORKER_STATUS);
        event.setInfo(status);
        event.setDateTime(new Date());
        data.add(event);
    }

    @Override
    public void newMealStatus(OrderMealStatus orderMealStatus, String status) {
        AuditEvent event = new AuditEvent();
        event.setType(AuditEventType.MEAL_STATUS);
        event.setInfo("order: " + orderMealStatus.getOrderId() + ", meal: " + orderMealStatus.getMenuItem().getName() + ", status: " + status);
        event.setDateTime(new Date());
        data.add(event);
    }

}
