package com.dmbb.cafe.repository.impl;

import com.dmbb.cafe.model.entity.MenuItem;
import com.dmbb.cafe.repository.MenuItemRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuItemRepositoryImpl extends SimpleInMemoryRepository<MenuItem> implements MenuItemRepository {

}
