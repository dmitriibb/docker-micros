package com.dmbb.springappa.service;

import com.dmbb.springappa.model.dto.TrayDTO;
import com.dmbb.springappa.model.entity.Food;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public interface RestRequestService {

    Map<String, Object> getMap(String fullApiUrl, Map<String, Object> params);

    Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params);

    String getString(String serviceName, String apiRL);

    Mono<String> getStringReactiveBalanced(String serviceName, String apiUrl);

    Mono<String> getStringReactive(String serviceName, String apiRL);

    List<String> cookFoodInServiceB(List<Food> foodList);

    TrayDTO getTrayFromServiceC(List<String> foodList);

}
