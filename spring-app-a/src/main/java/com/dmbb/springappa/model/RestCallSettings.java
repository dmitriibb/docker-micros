package com.dmbb.springappa.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestCallSettings {

    private boolean discoveryService;
    private boolean reactive;
    private boolean gateway;
    private boolean loadBalancer;

}
