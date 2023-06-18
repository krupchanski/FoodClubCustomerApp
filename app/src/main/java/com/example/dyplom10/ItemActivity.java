package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;
import static java.lang.Math.round;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Item itemReceived;
    Button btnBackToMenu;
    ImageView ivItem;
    TextView tvItemName;
    TextView tvCcal;
    LinearLayout llQuantity;
    TextView tvQuantity;
    Spinner spQuantity;
    int pickedQuantity;
    Button btnCustomize;
    //TextView tvNutritionInfo;
    RelativeLayout rlAddToOrder;
    TextView tvItemPrice;
    double itemPrice;
    RelativeLayout rlBtnCart;
    TextView tvCartSize;
    ImageView ivCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        Log.d(Global.TAG, "Item Activity onCreate");

        Intent intent = getIntent();
        //itemReceived = intent.getParcelableExtra("item");
        String itemJson = intent.getStringExtra("ITEM_JSON");
        itemReceived = JsonConverter.convertJsonToItem(itemJson);

        btnBackToMenu = findViewById(R.id.btnBackToMenuActivity);
        btnBackToMenu.setOnClickListener(this);
        ivItem = findViewById(R.id.ivItemImageItemActivity);
        tvItemName = findViewById(R.id.tvItemNameItemActivity);
        tvCcal = findViewById(R.id.tvItemCcalItemActivity);
        llQuantity = findViewById(R.id.rlQuantity);
        //tvQuantity = findViewById(R.id.tvQuantityItemActivity);
        spQuantity = findViewById(R.id.spQuantityItemActivity);
        btnCustomize = findViewById(R.id.btnCustomizeItemActivity);
        rlAddToOrder = findViewById(R.id.rlAddToOrder);
        rlAddToOrder.setOnClickListener(this::onClick);
        tvItemPrice = findViewById(R.id.tvItemPriceItemActivity);
        rlBtnCart = findViewById(R.id.rlBtnShoppingCart);
        rlBtnCart.setOnClickListener(this::onClick);
        tvCartSize = findViewById(R.id.tvCartSize);
        ivCart = findViewById(R.id.ivShoppingBagFromItemActivity);

        tvItemName.setText(itemReceived.getName());
        tvCcal.setText(String.valueOf(itemReceived.getCal()).concat(" ccal"));

        ArrayAdapter<Integer> quantityArrayAdapter = new ArrayAdapter<Integer>(ItemActivity.this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Integer>() {{
                    for (int i : getResources().getIntArray(R.array.quantity_array)) add(i);
                }});
        quantityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuantity.setAdapter(quantityArrayAdapter);
        spQuantity.setOnItemSelectedListener(this);

        //ivItem.setImageResource(itemReceived.getImg());
        //ivItem.setImageResource(getResources().getIdentifier(itemReceived.getImg(), "drawable", MenuActivity.this.getPackageName()));
        ivItem.setImageResource(getResources().getIdentifier("placeholder", "drawable", ItemActivity.this.getPackageName()));
        //btnCategory.setBackgroundResource(resources.getIdentifier(category.getIcon(), "drawable", MenuActivity.this.getPackageName()));

        tvItemPrice.setText("$".concat(String.valueOf(itemReceived.getPrice())));

    }

    @Override
    protected void onStart() {
        super.onStart();
        tvCartSize.setText(String.valueOf(MyMath.calculateCartSize()));

        Log.d(Global.TAG, "Item Activity onStart");

        rlBtnCart = findViewById(R.id.rlBtnShoppingCart);
        rlBtnCart.setOnClickListener(this::onClick);
        tvCartSize = findViewById(R.id.tvCartSize);
        ivCart = findViewById(R.id.ivShoppingBagFromItemActivity);
        if (Global.itemsInCart.isEmpty()) {
            rlBtnCart.setOnClickListener(null);
            rlBtnCart.setBackground(getResources().getDrawable(R.drawable.ripple_cart_disabled_button, null));
            tvCartSize.setTextColor(getResources().getColor(R.color.myBlack, null));
            ivCart.getDrawable().setColorFilter(getResources().getColor(R.color.paneraDarkGrey, null), PorterDuff.Mode.SRC_IN);
        } else {
            rlBtnCart.setOnClickListener(this::onClick);
            rlBtnCart.setBackground(getResources().getDrawable(R.drawable.ripple_cart_button, null));
            tvCartSize.setTextColor(getResources().getColor(R.color.paneraWhite, null));
            ivCart.getDrawable().setColorFilter(getResources().getColor(R.color.paneraWhite, null), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Global.TAG, "Item Activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Global.TAG, "Item Activity onPause");
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Global.TAG, "Item Activity onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Global.TAG, "Item Activity onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Global.TAG, "Item Activity onDestroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackToMenuActivity: {
                startActivity(new Intent(this, MenuActivity.class));
                break;
            }
            case R.id.rlBtnShoppingCart: {
                startActivity(new Intent(this, CartActivity.class));
                break;
            }
            case R.id.rlAddToOrder: {

                if (!rlBtnCart.hasOnClickListeners()) {
                    rlBtnCart.setOnClickListener(this::onClick);
                    rlBtnCart.setBackground(getResources().getDrawable(R.drawable.ripple_cart_button, null));
                    tvCartSize.setTextColor(getResources().getColor(R.color.paneraWhite, null));
                    ivCart.getDrawable().setColorFilter(getResources().getColor(R.color.paneraWhite, null), PorterDuff.Mode.SRC_IN);
                }

                itemReceived.setQuantity(itemReceived.getQuantity() + pickedQuantity);
                Global.itemsInCart.add(itemReceived);
                tvCartSize.setText(String.valueOf(MyMath.calculateCartSize()));

                startActivity(new Intent(this, MenuActivity.class));
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        pickedQuantity = (int) parent.getItemAtPosition(position);

//        itemReceived.setQuantity(itemReceived.getQuantity() + (pickedQuantity - 1));
        itemPrice = MyMath.round((itemReceived.getPrice() * pickedQuantity), 2);
        tvItemPrice.setText(String.format(Locale.getDefault(),"$%.2f", itemPrice));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
