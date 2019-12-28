package com.example.dyplom10;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    @DocumentId
    private String id;
    private Date date;
    private boolean dineIn;
    private int plateNumber;
    private Map<String, Item> itemsMap;
    private OrderStatus status;

    public Order() {
        this.id = "";
        this.date = Calendar.getInstance().getTime();
        this.dineIn = true;
        this.plateNumber = 0;
        this.itemsMap = new HashMap<>();
        this.status = OrderStatus.PENDING;
    }

    public Order(boolean dineIn, int plateNumber, Map<String, Item> itemsMap, OrderStatus status) {
        this.id = "";
        this.date = Calendar.getInstance().getTime();
        this.dineIn = dineIn;
        this.plateNumber = plateNumber;
        this.itemsMap = itemsMap;
        this.status = status;
    }

    public Order(Order other) {
        this.id = other.id;
        this.date = other.date;
        this.dineIn = other.dineIn;
        this.plateNumber = other.plateNumber;
        this.itemsMap = other.itemsMap;
        this.status = other.status;
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public Date getDate() {
        return date;
    }

    public boolean getDineIn() {
        return dineIn;
    }

    public void setDineIn(boolean dineIn) {
        this.dineIn = dineIn;
    }

    public int getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(int plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Map<String, Item> getItemsMap() {
        return itemsMap;
    }

    public void setItemsMap(Map<String, Item> itemsMap) {
        this.itemsMap = itemsMap;
    }

    public OrderStatus getStatus() { return status; }

    public void setStatus(OrderStatus status) { this.status = status; }
}
