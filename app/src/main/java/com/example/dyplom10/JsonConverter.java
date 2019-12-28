package com.example.dyplom10;

import com.google.gson.Gson;

public class JsonConverter {
    public static String convertOrderToJson(Order order) {
        Gson gson = new Gson();
        return gson.toJson(order);
    }
    public static Order convertJsonToOrder(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Order.class);
    }
    public static String convertItemToJson(Item item) {
        Gson gson = new Gson();
        return gson.toJson(item);
    }
    public static Item convertJsonToItem(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Item.class);
    }
}
