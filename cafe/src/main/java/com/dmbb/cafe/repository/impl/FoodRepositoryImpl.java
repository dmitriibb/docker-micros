package com.dmbb.cafe.repository.impl;

import com.dmbb.cafe.model.entity.Food;
import com.dmbb.cafe.repository.FoodRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FoodRepositoryImpl implements FoodRepository {

    @Override
    public List<Food> getAllFood() {
        return getFakeFoodList();
    }

    public List<Food> getFakeFoodList() {
        List<Food> list = new ArrayList<>();

        list.add(new Food("banana", "yellow", 5));
        list.add(new Food("banana", "green", 7));
        list.add(new Food("milk", "cow", 3));
        list.add(new Food("milk", "soy", 2));
        list.add(new Food("apple", "green", 12));
        list.add(new Food("apple", "red", 8));
        list.add(new Food("sugar", "white", 4));
        list.add(new Food("sugar", "brown", 3));
        list.add(new Food("pineapple", "yellow", 21));
        list.add(new Food("chocolate", "black", 22));
        list.add(new Food("coffee", "black", 8));

        return list;
    }

}
