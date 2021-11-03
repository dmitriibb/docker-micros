package com.dmbb.springappa.model.dto;

import com.dmbb.springappa.model.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FoodListDTO {

    private List<Food> foodList;

}
