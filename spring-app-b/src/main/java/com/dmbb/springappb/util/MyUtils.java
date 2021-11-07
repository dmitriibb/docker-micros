package com.dmbb.springappb.util;

import java.util.Random;

public class MyUtils {

    public static void delay() {
        int delay = new Random().nextInt(500) + 500;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
