package com.dmbb.cafe.controller;

import com.dmbb.cafe.model.entity.MenuItem;
import com.dmbb.cafe.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public List<MenuItem> getFullMenu() {
        return menuService.getFullMenu();
    }

}
