package com.dmbb.cafe.util;

import com.dmbb.cafe.model.entity.Food;
import com.dmbb.cafe.model.entity.MenuItem;
import com.dmbb.cafe.repository.FoodRepository;
import com.dmbb.cafe.repository.MenuItemRepository;
import com.dmbb.cafe.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TestDataInitializer {

    private final FoodRepository foodRepository;
    private final MenuItemRepository menuItemRepository;

    @PostConstruct
    public void init() {
        initFood();
        initMenuItems();
    }

    private void initMenuItems() {
        MenuItem coffee1 = new MenuItem();
        coffee1.setName("coffee");
        coffee1.setDescription("simple coffee with sugar and milk");
        coffee1.addIngredient("coffee", 1);
        coffee1.addIngredient("milk", 1);
        coffee1.addIngredient("sugar", 2);
        coffee1.setCost(20);
        coffee1.setWeight(200);
        coffee1.setTime(2000);
        menuItemRepository.save(coffee1);

        MenuItem cake = new MenuItem();
        cake.setName("cake");
        cake.setDescription("cake with banana");
        cake.addIngredient("banana", 2);
        cake.addIngredient("wheat", 2);
        cake.addIngredient("sugar", 2);
        cake.addIngredient("egg", 4);
        cake.setCost(50);
        cake.setWeight(700);
        cake.setTime(4000);
        menuItemRepository.save(cake);

        MenuItem brownie = new MenuItem();
        brownie.setName("brownie");
        brownie.setDescription("simple brownie");
        brownie.addIngredient("chocolate", 4);
        brownie.addIngredient("wheat", 2);
        brownie.addIngredient("sugar", 2);
        brownie.addIngredient("egg", 4);
        brownie.setCost(60);
        brownie.setWeight(600);
        brownie.setTime(6000);
        menuItemRepository.save(brownie);
    }

    private void initFood() {
        foodRepository.save(new Food("banana", 5));
        foodRepository.save(new Food("milk", 3));
        foodRepository.save(new Food("apple", 8));
        foodRepository.save(new Food("sugar", 3));
        foodRepository.save(new Food("pineapple", 21));
        foodRepository.save(new Food("chocolate", 22));
        foodRepository.save(new Food("coffee", 8));
        foodRepository.save(new Food("wheat", 4));
        foodRepository.save(new Food("egg", 7));
    }

}
