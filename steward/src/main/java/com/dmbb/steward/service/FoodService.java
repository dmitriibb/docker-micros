package com.dmbb.steward.service;

import com.dmbb.steward.model.dto.TrayDTO;

import java.util.List;

public interface FoodService {

    TrayDTO putOnTray(List<String> foodList);

}
