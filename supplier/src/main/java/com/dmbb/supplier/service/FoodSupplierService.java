package com.dmbb.supplier.service;

import com.dmbb.supplier.model.kafka.FoodSupplyDTO;

public interface FoodSupplierService {

    void processFoodSupplyOrder(FoodSupplyDTO foodSupplyDTO);

}
