package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DineInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBack;
    Button btnDineIn;
    Button btnToGo;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnBackToCart);
        btnBack.setOnClickListener(this);
        btnDineIn = findViewById(R.id.btnDineIn);
        btnDineIn.setOnClickListener(this::onClick);
        btnToGo = findViewById(R.id.btnToGo);
        btnToGo.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackToCart: {
                startActivity(new Intent(this, CartActivity.class));
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case R.id.btnDineIn: {
                saveDineInValue(true);
                startActivity(new Intent(this, NumberPlateActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
            case R.id.btnToGo: {
                saveDineInValue(false);
                startActivity(new Intent(this, NumberPlateActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }
    }
    private void saveDineInValue(boolean isDineIn) {
        sPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        editor = sPref.edit();
        editor.putBoolean("isDineIn", isDineIn);
        editor.apply();
    }
}
