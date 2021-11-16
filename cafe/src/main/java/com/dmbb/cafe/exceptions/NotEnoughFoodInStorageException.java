package com.dmbb.cafe.exceptions;

public class NotEnoughFoodInStorageException extends Exception{

    public NotEnoughFoodInStorageException(String food, int requiredAmount, Integer currentAmount) {
        super("Not enough food in the storage. " + food + ": required=" + requiredAmount + ", current=" + currentAmount);
    }
}
