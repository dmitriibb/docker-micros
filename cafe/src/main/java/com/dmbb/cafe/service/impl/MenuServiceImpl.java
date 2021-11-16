package com.dmbb.cafe.service.impl;

import com.dmbb.cafe.model.entity.MenuItem;
import com.dmbb.cafe.repository.MenuItemRepository;
import com.dmbb.cafe.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getFullMenu() {
        return menuItemRepository.getAll();
    }

    @Override
    public MenuItem getMenuItemByName(String name) {
        return menuItemRepository.getByName(name);
    }
}
