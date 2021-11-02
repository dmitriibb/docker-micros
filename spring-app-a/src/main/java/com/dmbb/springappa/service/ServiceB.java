package com.dmbb.springappa.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface ServiceB {

    Map<String, Object> getInfoViaEureka();

    Map<String, Object> getInfoDirect();

    Map<String, Object> getInfoViaHystrix(int delay, int timeout);

    Map<String, Object> getInfoError();

}
