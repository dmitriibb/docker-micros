package com.dmbb.springappc.service;

import com.dmbb.springappc.model.dto.TrayDTO;

import java.util.List;

public interface FoodService {

    TrayDTO putOnTray(List<String> foodList);

}
