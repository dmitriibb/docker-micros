package com.dmbb.cafe.service;

import java.util.Map;

public interface ServiceB {

    Map<String, Object> getInfoViaEureka();

    Map<String, Object> getInfoViaHystrix(int delay, int timeout);

    Map<String, Object> getInfoError();

}
