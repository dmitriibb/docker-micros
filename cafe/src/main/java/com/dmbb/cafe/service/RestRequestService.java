package com.dmbb.cafe.service;

import com.dmbb.cafe.model.RestCallSettings;
import com.dmbb.cafe.model.dto.TrayDTO;
import com.dmbb.cafe.model.entity.Food;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface RestRequestService {

    Map<String, Object> getMap(String fullApiUrl, Map<String, Object> params);

    Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params);

    String getString(String serviceName, String apiRL);

    Mono<String> getString(String serviceName, String api, RestCallSettings settings);

    List<String> cookFood(List<Food> foodList, RestCallSettings settings);

    Mono<List<String>> cookFoodReactive(List<Food> foodList, RestCallSettings settings);

    TrayDTO getTrayWithCookedFood(List<String> foodList, RestCallSettings settings);

}
