package com.dmbb.springappa.model.dto;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AggregationDTO {

    private Map<String, Object> metrics = new HashMap<>();

    public void addMetric(String metricName, Object value) {
        metrics.put(metricName, value);
    }

}
