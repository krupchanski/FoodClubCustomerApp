package com.example.dyplom10;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String name;
    private double price;
    private int cal;
    private String img;
    private int quantity;
    private boolean isAvailable;
    private List<String> categories;

    public Item(){ }

    public Item(Item other, @Nullable List<String> categories) {
        this.name = other.name;
        this.price = other.price;
        this.cal = other.cal;
        this.img = other.img;
        this.quantity = 0;
        this.isAvailable = other.isAvailable;
        if(categories != null)
            this.categories = new ArrayList<>(categories);
        else
            this.categories = new ArrayList<>();
    }

    public Item(String name, double price, int cal, String img, @Nullable List<String> categories) {
        this.name = name;
        this.price = price;
        this.cal = cal;
        this.img = img;
        this.quantity = 0;
        this.isAvailable = true;
        if(categories != null) {
            this.categories = new ArrayList<>(categories);
        }
        else {
            this.categories = new ArrayList<>();
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() { return price; }

    public int getCal() {
        return cal;
    }

    public String getImg() {
        return img;
    }

    public int getQuantity() { return quantity;}

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean getIsAvailable() { return isAvailable; }

    public void setIsAvailable (boolean availability) { this.isAvailable = availability; }

    public List<String> getCategories() { return this.categories; }

    public void setCategories(List<String> categories) { this.categories = categories; }

    public void incrementQuantity() { this.quantity++; }

    public void decrementQuantity() { this.quantity--; }

    public void addCategory(String category) {
        if(categories != null)
            categories.add(category);
    }
}
