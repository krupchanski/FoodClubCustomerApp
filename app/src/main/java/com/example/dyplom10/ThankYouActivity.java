package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThankYouActivity extends AppCompatActivity implements View.OnClickListener, OrderIdInterface {

    TextView tvTotalItems;
    TextView tvOrderPrice;
    TextView tvDineIn;
    TextView tvOrderId;
    TextView tvImFinished;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        tvTotalItems = findViewById(R.id.tvTotalItemsCount);
        tvOrderPrice= findViewById(R.id.tvTotalPrice);
        tvDineIn = findViewById(R.id.tvDineInValue);
        tvOrderId = findViewById(R.id.tvOrderNumber);
        tvImFinished = findViewById(R.id.tvImFinished);
        tvImFinished.setOnClickListener(this::onClick);

        String jsonOrder = getIntent().getStringExtra("ORDER_JSON");
        Order order = JsonConverter.convertJsonToOrder(jsonOrder);

        String orderId = order.getId();
        Map<String, Item> itemsInOrderMap = order.getItemsMap();
        int orderSize = MyMath.calculateCartSize(itemsInOrderMap);
        boolean dineIn = order.getDineIn();
        int plateNumber = order.getPlateNumber();

        if(order.getItemsMap().size() == 1)
            tvTotalItems.setText(String.valueOf(orderSize).concat(" item"));
        else
            tvTotalItems.setText(String.valueOf(orderSize).concat(" items"));

        if(dineIn)
            tvDineIn.setText("Dine In");
        else
            tvDineIn.setText("To Go");

        tvOrderPrice.setText("$".concat(String.valueOf(MyMath.calculateCartPrice(itemsInOrderMap))));
        tvOrderId.setText("Order ID: " + orderId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvImFinished: {
                startActivity(new Intent(this, WelcomeActivity.class));
                break;
            }
        }
    }

    @Override
    public void setOrderId(String orderId) {
        tvOrderId.setText(orderId);
    }
}
