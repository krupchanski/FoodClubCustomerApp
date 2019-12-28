package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBackToWelcomeActivity;
    Button btnContinueAsGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        btnBackToWelcomeActivity = (Button) findViewById(R.id.btnBackToWelcomeActivity);
        btnBackToWelcomeActivity.setOnClickListener(this);
        btnContinueAsGuest = (Button) findViewById(R.id.btnContinueAsGuest);
        btnContinueAsGuest.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Global.itemsInCart.isEmpty()) Global.itemsInCart.clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackToWelcomeActivity: {
                startActivity(new Intent(this, WelcomeActivity.class));
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case R.id.btnContinueAsGuest: {
                startActivity((new Intent(this, MenuActivity.class)));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
