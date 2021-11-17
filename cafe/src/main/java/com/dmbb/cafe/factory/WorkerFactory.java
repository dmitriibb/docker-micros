package com.dmbb.cafe.factory;

import com.dmbb.cafe.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerFactory {

    private final FoodService foodService;
    private final AuditService auditService;
    private final SupplierService supplierService;
    private final OrderStatusService orderStatusService;

    public SimpleWorker getWorker(String name, OrderManagerService orderManagerService) {
        return new SimpleWorker(name, foodService, auditService, supplierService, orderStatusService, orderManagerService);
    }

}
