package com.dmbb.cafe.repository;

import com.dmbb.cafe.model.entity.MenuItem;

import java.util.List;

public interface MenuItemRepository {

    List<MenuItem> getAll();

    MenuItem getByName(String name);

    MenuItem save(MenuItem menuItem);

}
