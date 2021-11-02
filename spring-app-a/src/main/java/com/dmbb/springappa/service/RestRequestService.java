package com.dmbb.springappa.service;

import java.util.Map;

public interface RestRequestService {

    Map<String, Object> getMap(String fullApiUrl, Map<String, Object> params);

    Map<String, Object> getMap(String serviceName, String apiUrl, Map<String, Object> params);

}
