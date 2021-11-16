package com.dmbb.cafe.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class MenuItem extends BaseEntity{

    private String description;
    private int weight;
    private int cost;
    private long time;

    private Map<String, Integer> ingredients = new HashMap<>();

    public void addIngredient(String name, int number) {
        ingredients.put(name, number);
    }

}
