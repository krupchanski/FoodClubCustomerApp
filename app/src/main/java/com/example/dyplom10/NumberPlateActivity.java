package com.example.dyplom10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberPlateActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnBack;
    TextView tvNoPlates;
    EditText etPlateNumber;
    Context context = this;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_plate);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        btnBack = findViewById(R.id.btnBackDineIn);
        btnBack.setOnClickListener(this::onClick);

        tvNoPlates = findViewById(R.id.tvNoPlatesAvailable);
        tvNoPlates.setOnClickListener(this::onClick);

        etPlateNumber = findViewById(R.id.etPlateNumber);
        etPlateNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(etPlateNumber.getText().toString().matches("")) {
                        Toast.makeText(context, "Please enter valid plate number", Toast.LENGTH_LONG).show();
                    }
                    else if(Integer.parseInt(etPlateNumber.getText().toString()) < 100 && Integer.parseInt(etPlateNumber.getText().toString()) != 0) {
                        savePlateNumber(Integer.parseInt(etPlateNumber.getText().toString()));
                        startActivity(new Intent(context, ProcessingActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else { Toast.makeText(context, "Please enter valid plate number", Toast.LENGTH_LONG).show(); }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackDineIn: {
                startActivity(new Intent(this, DineInActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case R.id.tvNoPlatesAvailable: {
                savePlateNumber(0);
                startActivity(new Intent(this, ProcessingActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }
    }

    private void savePlateNumber(int plateNumber) {
        sPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        editor = sPref.edit();
        editor.putInt("plateNumber", plateNumber);
        editor.apply();
    }
}
