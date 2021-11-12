package com.dmbb.cafe.model.dto;

import com.dmbb.cafe.model.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FoodListDTO {

    private List<Food> foodList;

}
