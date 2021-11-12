package com.dmbb.steward.service.impl;

import com.dmbb.steward.model.dto.TrayDTO;
import com.dmbb.steward.service.FoodService;
import com.dmbb.steward.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Override
    public TrayDTO putOnTray(List<String> foodList) {
        log.info("putting food on the tray");
        MyUtils.delay();

        TrayDTO trayDTO = new TrayDTO();
        trayDTO.setContent(foodList);
        return trayDTO;
    }

}
