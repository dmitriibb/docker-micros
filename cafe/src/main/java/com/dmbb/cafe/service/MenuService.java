package com.dmbb.cafe.service;

import com.dmbb.cafe.model.entity.MenuItem;

import java.util.List;

public interface MenuService {

    List<MenuItem> getFullMenu();

    MenuItem getMenuItemByName(String name);

}
