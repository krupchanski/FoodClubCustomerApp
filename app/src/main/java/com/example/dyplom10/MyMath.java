package com.example.dyplom10;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;

public class MyMath {
    public static double round(double value, int places) {
        if(places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static int calculateCartSize() {
        int result = 0;
        for (Item item : Global.itemsInCart) {
            result += item.getQuantity();
        }
        return result;
    }
    public static int calculateCartSize(Map<String, Item> itemsMap) {
        int size = 0;
        for(Map.Entry mapElement : itemsMap.entrySet()) {
            size += ((Item)mapElement.getValue()).getQuantity();
        }
        return size;
    }
    public static double calculateCartPrice() {
        double result = 0;
        for (Item item : Global.itemsInCart) {
            result += item.getPrice() * item.getQuantity();
        }
        return result;
    }
    public static double calculateCartPrice(Map<String, Item> itemsMap) {
        double result = 0;
        for(Map.Entry mapElement : itemsMap.entrySet()) {
            result += ((Item)mapElement.getValue()).getPrice() * ((Item)mapElement.getValue()).getQuantity();
        }
        return result;
    }
}
