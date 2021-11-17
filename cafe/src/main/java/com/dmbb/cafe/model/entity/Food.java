package com.dmbb.cafe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseEntity{

    private int number;

    public Food(String name, int number) {
        setName(name);
        this.number = number;
    }
}
