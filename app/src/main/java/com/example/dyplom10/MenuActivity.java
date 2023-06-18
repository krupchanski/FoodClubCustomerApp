package com.example.dyplom10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.StateListAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rlBtnCart;
    TextView tvCartSize;
    ImageView ivCart;
    TextView tvQuit;
    TextView tvPickedCategory;
    List<Category> categories;
    List<Item> filteredItems = new ArrayList<>();
    List<LinearLayout> llSingleCategoriesArr;
    List<RelativeLayout> rlSingleCategoriesArr;
    RelativeLayout rlActivityMenu;
    HorizontalScrollView hsvMenuCategories;
    GridLayout glMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        // Hide the action bar
        getSupportActionBar().hide();

        Log.d(Global.TAG, "Menu Activity onCreate");

        Resources resources = this.getResources();

        rlActivityMenu = findViewById(R.id.rlActivityMenu);
        ivCart = findViewById(R.id.ivShoppingBag);
        tvCartSize = findViewById(R.id.tvCartSize);
        rlBtnCart = findViewById(R.id.rlBtnShoppingCart);
        rlBtnCart.setOnClickListener(this::onClick);
        if(Global.itemsInCart.isEmpty()) {
            rlBtnCart.setOnClickListener(null);
            rlBtnCart.setBackground(getResources().getDrawable(R.drawable.ripple_cart_disabled_button, null));
            tvCartSize.setTextColor(getResources().getColor(R.color.myBlack));
            ivCart.setColorFilter(R.color.paneraDarkGrey);
        }


        tvQuit = findViewById(R.id.tvQuit);
        tvQuit.setOnClickListener(this);
        tvPickedCategory = findViewById(R.id.tvPickedCategory);

        hsvMenuCategories = findViewById(R.id.svMenuCategories);
        glMenuItems = findViewById(R.id.glMenuItems);

        categories = new ArrayList<>();

        llSingleCategoriesArr = new ArrayList<>();    // this array contains llSingleCategory views that are inside llMenuCategories viewArrayList<RelativeLayout>
        rlSingleCategoriesArr = new ArrayList<>();    // this array contains rl's that are inside llSingleCategory views, one child per one parent

        // -- // -- // -- // -- // -- // -- // -- // -- // --
        // ADDING CATEGORY BUTTONS ON SCREEN
        // -- // -- // -- // -- // -- // -- // -- // -- // --

        LinearLayout llMenuCategories = findViewById(R.id.llCategories);

        RelativeLayout.LayoutParams layoutParamsForRLSingleCategory = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 88, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 88, getResources().getDisplayMetrics()));

        LinearLayout.LayoutParams layoutParamsForSingleCategory = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsForSingleCategory.setMarginStart((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()));
        layoutParamsForSingleCategory.setMarginEnd((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()));

        LinearLayout.LayoutParams layoutParamsButtonCategory = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.btn_category_style);

        for (Category category : Global.categories) {
            LinearLayout llSingleCategory = new LinearLayout(this);
            llSingleCategory.setOrientation(LinearLayout.VERTICAL);

            RelativeLayout rlSingleCategory = new RelativeLayout(contextThemeWrapper);
            rlSingleCategory.setLayoutParams(layoutParamsForRLSingleCategory);
            rlSingleCategory.setGravity(Gravity.CENTER);
            rlSingleCategory.setBackgroundResource(R.drawable.round_category_background_button);

            final Button btnCategory = new Button(contextThemeWrapper);

            btnCategory.setLayoutParams(layoutParamsButtonCategory);
            btnCategory.setBackgroundResource(R.drawable.barn);
            btnCategory.setBackgroundResource(resources.getIdentifier(category.getIcon(), "drawable", MenuActivity.this.getPackageName()));
            btnCategory.getBackground().setColorFilter(resources.getColor(R.color.paneraDarkGrey, null), PorterDuff.Mode.SRC_IN);
            btnCategory.setGravity(Gravity.CENTER);
            btnCategory.setClickable(false);

            rlSingleCategory.setTag("rl" + category.getName().replaceAll("\\s", ""));
            rlSingleCategory.setClickable(true);
            rlSingleCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        for (int j = 0; j < llMenuCategories.getChildCount(); j++) {
                            llSingleCategoriesArr.add(j, (LinearLayout) llMenuCategories.getChildAt(j));
                            rlSingleCategoriesArr.add(j, (RelativeLayout) llSingleCategoriesArr.get(j).getChildAt(0));
                            rlSingleCategoriesArr.get(j).setBackgroundResource(R.drawable.round_category_background_button);

                            rlSingleCategoriesArr.get(j).getChildAt(0).setBackgroundResource(resources.getIdentifier(Global.categories.get(j).getIcon(), "drawable", MenuActivity.this.getPackageName()));
                            rlSingleCategoriesArr.get(j).getChildAt(0).getBackground().setColorFilter(resources.getColor(R.color.paneraDarkGrey, null), PorterDuff.Mode.SRC_IN);
                        }
                    } catch (Exception e) {
                        Log.d(Global.TAG, "llMenuCategories has" + llMenuCategories.getChildCount() + " children", e);
                    }

                    rlSingleCategory.setBackgroundResource(R.drawable.green_round_category_background_button);
                    btnCategory.getBackground().setColorFilter(resources.getColor(R.color.paneraWhite, null), PorterDuff.Mode.SRC_IN);
                    TextView tvDefaultCategory = (TextView) llSingleCategory.getChildAt(1);
                    tvPickedCategory.setText(tvDefaultCategory.getText().toString());
                    updateMenu(tvPickedCategory.getText().toString());
                }
            });

            LinearLayout.LayoutParams lpForTvCategory = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lpForTvCategory.setMargins(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()),
                    0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));

            TextView tvCategory = new TextView(this);
            tvCategory.setText(category.getName());
            tvCategory.setTextColor(getResources().getColor(R.color.myBlack, null));
            tvCategory.setGravity(Gravity.CENTER_HORIZONTAL);
            tvCategory.setLayoutParams(lpForTvCategory);

            rlSingleCategory.addView(btnCategory);
            llSingleCategory.addView(rlSingleCategory);
            llSingleCategory.addView(tvCategory);
            llMenuCategories.addView(llSingleCategory, layoutParamsForSingleCategory);
        }
        LinearLayout ll = (LinearLayout) llMenuCategories.getChildAt(1);
        TextView tv = (TextView) ll.getChildAt(1);
        tvPickedCategory.setText(tv.getText().toString());
        ll.getChildAt(0).performClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Global.TAG, "Menu Activity onStart");

        tvCartSize.setText(String.valueOf(MyMath.calculateCartSize()));

        ivCart = findViewById(R.id.ivShoppingBag);
        tvCartSize = findViewById(R.id.tvCartSize);
        rlBtnCart = findViewById(R.id.rlBtnShoppingCart);
        rlBtnCart.setOnClickListener(this::onClick);
        if(Global.itemsInCart.isEmpty()) {
            rlBtnCart.setOnClickListener(null);
            rlBtnCart.setBackground(getResources().getDrawable(R.drawable.ripple_cart_disabled_button, null));
            tvCartSize.setTextColor(getResources().getColor(R.color.myBlack));
            ivCart.getDrawable().setColorFilter(getResources().getColor(R.color.paneraDarkGrey, null), PorterDuff.Mode.SRC_IN);
        } else {
            rlBtnCart.setOnClickListener(this::onClick);
            rlBtnCart.setBackground(getResources().getDrawable(R.drawable.ripple_cart_button, null));
            tvCartSize.setTextColor(getResources().getColor(R.color.paneraWhite));
            ivCart.getDrawable().setColorFilter(getResources().getColor(R.color.paneraWhite, null), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.tvQuit): {
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            }
            case (R.id.rlBtnShoppingCart): {
                startActivity(new Intent(this, CartActivity.class));
                break;
            }
        }
    }

    public void updateMenu(String category) {
        glMenuItems.removeAllViews();
        filteredItems.clear();
        for(Item item : Global.items) {
            if(item.getCategories().contains(category)) { filteredItems.add(item); }
        }

        Context context = this;
        glMenuItems = findViewById(R.id.glMenuItems);
        Log.d(Global.TAG, "UpdateMenuMethod: items size: " + filteredItems.size());
        for (int i = 0; i < filteredItems.size(); i++) {
            View menuItemLayout = LayoutInflater.from(this).inflate(R.layout.menu_item, null);

            LinearLayout llMenuItem = menuItemLayout.findViewById(R.id.llMenuItem);
            llMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startItemActivityIntent = new Intent(context, ItemActivity.class);
                    Item item = filteredItems.get(glMenuItems.indexOfChild(llMenuItem));
                    String itemJson = JsonConverter.convertItemToJson(item);
                    startItemActivityIntent.putExtra("ITEM_JSON", itemJson);
                    startActivity(startItemActivityIntent);
                }
            });
            ImageView ivMenuItem = menuItemLayout.findViewById(R.id.ivMenuItemImage);
            TextView tvMenuItemName = menuItemLayout.findViewById(R.id.tvItemName);
            TextView tvPrice = menuItemLayout.findViewById(R.id.tvItemPrice);
            TextView tvCcal = menuItemLayout.findViewById(R.id.tvItemCcal);

            //ivMenuItem.setImageResource(Global.filteredItems.get(i).getImg());
            ivMenuItem.setImageResource(getResources().getIdentifier("placeholder", "drawable", MenuActivity.this.getPackageName()));
            tvMenuItemName.setText(String.valueOf(filteredItems.get(i).getName()));
            tvPrice.setText("$".concat(String.valueOf(filteredItems.get(i).getPrice())));
            tvCcal.setText(String.valueOf(filteredItems.get(i).getCal()).concat(" ccal"));

            glMenuItems.addView(llMenuItem);
        }
    }

}
