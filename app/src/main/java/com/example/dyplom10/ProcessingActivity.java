package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingActivity extends AppCompatActivity implements StartThankYouInterface{

    SharedPreferences sPref;
    Context context = this;
    ImageView ivCroissant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();
        Log.d(Global.TAG, "ProcessingActivity onCreate");

        ivCroissant = findViewById(R.id.ivProcessingCroissant);
        ivCroissant.getDrawable().setColorFilter(getResources().getColor(R.color.paneraWhite, null), PorterDuff.Mode.SRC_IN);

        boolean dineIn = loadDineIn();
        int plateNumber = loadPlateNumber();
        List<Item> itemsInCart = Global.itemsInCart;
        Map<String, Item> itemsMap = new HashMap<>();
        for(Item item : itemsInCart) {
            itemsMap.put(item.getName(), item);
        }

        Order order = new Order(dineIn, plateNumber, itemsMap, OrderStatus.PENDING);
        DBHelper.sendOrder(order, this, this);
    }
    public boolean loadDineIn() {
        sPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        return sPref.getBoolean("isDineIn", true);
    }
    public int loadPlateNumber() {
        sPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        return sPref.getInt("plateNumber", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Global.TAG, "ProcessingActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Global.TAG, "ProcessingActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Global.TAG, "ProcessingActivity onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Global.TAG, "ProcessingActivity onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Global.TAG, "ProcessingActivity onDestroy");
    }

    @Override
    public void startThankYouActivityWithOrderId(Order order, int TIME_OUT) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String jsonOrder = JsonConverter.convertOrderToJson(order);
                Log.d(Global.TAG, "startThankYouActivityWithOrderId in Processing:\n" + jsonOrder);
                Intent intent = new Intent(context, ThankYouActivity.class);
                intent.putExtra("ORDER_JSON", jsonOrder);
                context.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, TIME_OUT);
    }
}
