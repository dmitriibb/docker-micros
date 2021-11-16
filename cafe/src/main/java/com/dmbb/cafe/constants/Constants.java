package com.dmbb.cafe.constants;

public class Constants {

    public static final String SERVICE_NAME_KITCHEN = "kitchen";
    public static final String SERVICE_NAME_STEWARD = "steward";
    public static final String SERVICE_NAME_GATEWAY = "gateway";

    public static final String KAFKA_GROUP_ID = "kafka_cafe_group";
    public static final String KAFKA_TOPIC_NOTIFICATION = "notifications";
    public static final String KAFKA_TOPIC_SUPPLY_FOOD_ORDER = "supply_food_orders";
    public static final String KAFKA_TOPIC_SUPPLY_FOOD = "supply_food";

    public static final String ORDER_STATUS_RECEIVED = "Received";
    public static final String ORDER_STATUS_READY = "Ready";
    public static final String ORDER_STATUS_DONE = "Done";

    public static final String MEAL_STATUS_RECEIVED = "Received";
    public static final String MEAL_STATUS_IN_PROGRESS = "In progress";
    public static final String MEAL_STATUS_DONE = "Done";
    public static final String MEAL_STATUS_COOKING = "Cooking";
    public static final String MEAL_STATUS_WAITING_INGREDIENTS = "Waiting ingredients";


    public static final String FOOD_ENOUGH_KEY = "FOOD_ENOUGH";
    public static final int FOOD_ENOUGH_YES = 1;
    public static final int FOOD_ENOUGH_NO = 0;

}
