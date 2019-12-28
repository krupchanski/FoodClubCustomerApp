package com.example.dyplom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.example.dyplom10.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Button btnBackToMenu;
    TextView tvItemsInOrder;
    RelativeLayout rlCheckout;
    TextView tvCheckoutValue;
    Spinner spQuantity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        Log.d(Global.TAG, "Cart Activity onCreate");

        btnBackToMenu = findViewById(R.id.btnBackToMenuActivityFromCart);
        btnBackToMenu.setOnClickListener(this);
        tvItemsInOrder = findViewById(R.id.tvItemsInOrder);
        spQuantity = findViewById(R.id.spQuantityInCart);

        loadItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Global.TAG, "Cart Activity onStart");

        updateQuantityOfItemsInCart();
        updateCheckoutButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Global.TAG, "Cart Activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Global.TAG, "Cart Activity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Global.TAG, "Cart Activity onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Global.TAG, "Cart Activity onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Global.TAG, "Cart Activity onDestroy");
    }

    private void updateQuantityOfItemsInCart() {
        tvItemsInOrder = findViewById(R.id.tvItemsInOrder);
        if(MyMath.calculateCartSize() == 1) { tvItemsInOrder.setText(String.valueOf(MyMath.calculateCartSize()).concat(" Item In Your Order")); }
        else { tvItemsInOrder.setText(String.valueOf(MyMath.calculateCartSize()).concat(" Items In Your Order")); }
    }

    private void updateCheckoutButton() {
        rlCheckout = findViewById(R.id.rlCheckout);
        tvCheckoutValue = findViewById(R.id.tvCartValueItemActivity);

        if(Global.itemsInCart.isEmpty()) {
            rlCheckout.setOnClickListener(null);
            rlCheckout.setBackground(getResources().getDrawable(R.drawable.ripple_cart_disabled_button, null));
            ((TextView)rlCheckout.getChildAt(0)).setTextColor(getResources().getColor(R.color.myBlack, null));
            tvCheckoutValue.setTextColor(getResources().getColor(R.color.myBlack, null));
            tvCheckoutValue.setText("$0.00");
        } else {
            rlCheckout.setOnClickListener(this::onClick);
            rlCheckout.setBackground(getResources().getDrawable(R.drawable.ripple_round_green_btn, null));
            ((TextView)rlCheckout.getChildAt(0)).setTextColor(getResources().getColor(R.color.paneraWhite, null));
            tvCheckoutValue.setTextColor(getResources().getColor(R.color.paneraWhite, null));

            double res = MyMath.calculateCartPrice();
            tvCheckoutValue.setText(String.format(Locale.getDefault(),"$%.2f", MyMath.round(res, 2)));
        }
    }

    private void loadItems() {
        LinearLayout llItemsInCart = findViewById(R.id.llItemsInCart);

        ArrayAdapter<Integer> quantityArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Integer>() {{
                    for(int i : getResources().getIntArray(R.array.quantity_array)) add(i);
                }});
        quantityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for(int i = 0; i < Global.itemsInCart.size(); i++) {
            View cartItemView = LayoutInflater.from(this).inflate(R.layout.cart_item, null);

            LinearLayout llCartItem = cartItemView.findViewById(R.id.llCartItem);
            llCartItem.setTag(Global.itemsInCart.get(i));

            ImageView ivOfItem = cartItemView.findViewById(R.id.ivItemInCart);
            TextView tvItemName = cartItemView.findViewById(R.id.tvItemNameInCart);
            TextView tvItemCal = cartItemView.findViewById(R.id.tvItemCalInCart);
            TextView tvItemPrice = cartItemView.findViewById(R.id.tvItemPriceInCart);
            Spinner spQuantity = cartItemView.findViewById(R.id.spQuantityInCart);
            Button btnRemoveItem = cartItemView.findViewById(R.id.btnRemoveItemCart);
            btnRemoveItem.setOnClickListener(this::onClick);

            tvItemName.setText(Global.itemsInCart.get(i).getName());
            tvItemCal.setText(String.valueOf(Global.itemsInCart.get(i).getCal()).concat(" Cal"));
            tvItemPrice.setText("$".concat(String.valueOf(Global.itemsInCart.get(i).getPrice())));

            spQuantity.setAdapter(quantityArrayAdapter);
            spQuantity.setOnItemSelectedListener(this);

            if(Global.itemsInCart.get(i).getQuantity() > 10) {

            }
            else {
                spQuantity.setSelection(Global.itemsInCart.get(i).getQuantity() - 1);
            }



            llItemsInCart.addView(llCartItem);
            if(i == Global.itemsInCart.size() - 1) {
                View tvAddInstructions = LayoutInflater.from(this).inflate(R.layout.add_special_intructions_view, null);
                llItemsInCart.addView(tvAddInstructions);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackToMenuActivityFromCart: {
                startActivity(new Intent(this, MenuActivity.class));
                break;
            }
            case R.id.btnRemoveItemCart: {
                LinearLayout llItemsInCart = findViewById(R.id.llItemsInCart);
                LinearLayout ll = (LinearLayout) v.getParent();
                Item item = (Item) ll.getTag();

                Global.itemsInCart.remove(item);
                llItemsInCart.removeView((LinearLayout)v.getParent());

                updateCheckoutButton();
                updateQuantityOfItemsInCart();
                break;
            }
            case R.id.rlCheckout: {
                startActivity(new Intent(this, DineInActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout ll= (LinearLayout)view.getParent().getParent();
        TextView tv = (TextView) ll.getChildAt(2);
        Item item = (Item) ll.getTag();
        Global.itemsInCart.get(Global.itemsInCart.indexOf(item)).setQuantity((int)parent.getItemAtPosition(position));
        tv.setText(String.format(Locale.getDefault(),"$%.2f", item.getPrice() * item.getQuantity()));
        updateQuantityOfItemsInCart();
        updateCheckoutButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
