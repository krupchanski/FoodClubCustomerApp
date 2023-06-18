package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rlHomeScreen;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        rlHomeScreen = (RelativeLayout) findViewById(R.id.rlHomeScreen);
        rlHomeScreen.setOnClickListener(this);

        ClassForTestingStuff c = new ClassForTestingStuff();




        DBHelper.loadCategories();
        DBHelper.loadItems();

        sPref = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        editor = sPref.edit();
        editor.clear();
        editor.apply();
    }








    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rlHomeScreen: {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
                break;
            }
            default: break;
        }
    }
}
