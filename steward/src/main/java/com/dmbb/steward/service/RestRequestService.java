package com.dmbb.steward.service;

import java.util.Map;

public interface RestRequestService {

    Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params);

    String getHomeInfo(String serviceName);

}
