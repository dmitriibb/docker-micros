package com.dmbb.cafe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    private int id;
    private String message;

    public Notification(String message) {
        this.message = message;
    }
}
