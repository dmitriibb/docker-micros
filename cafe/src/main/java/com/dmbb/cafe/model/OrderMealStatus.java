package com.dmbb.cafe.model;

import com.dmbb.cafe.model.entity.MenuItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
public class OrderMealStatus implements Comparable<OrderMealStatus>{

    private static final String DATE_TIME_FORMAT = "YYYY-MM-DD HH:mm:ss";

    private String id;

    private String orderId;

    private MenuItem menuItem;

    private String status;

    private Date receivedTime = new Date();

    private List<String> progress = new ArrayList<>();

    public void setStatus(String status) {
        this.status = status;
        progress.add(new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date()) + " - " +status);
    }

    @Override
    public String toString() {
        return "meal: " + menuItem.getName() + ", orderId: " + orderId + ", id: " + id;
    }

    @Override
    public int compareTo(OrderMealStatus o) {
        return receivedTime.compareTo(o.receivedTime);
    }
}
