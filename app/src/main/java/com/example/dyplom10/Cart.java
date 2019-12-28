package com.example.dyplom10;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.Menu;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Item> items;
    public Cart() {
        items = new ArrayList<>();
    }
    public void addItem(Item item) {
        items.add(item);
    }
    public void deleteItem(Item item) {
        if(items.contains(item)) {
            items.remove(item);
        }
    }
}
