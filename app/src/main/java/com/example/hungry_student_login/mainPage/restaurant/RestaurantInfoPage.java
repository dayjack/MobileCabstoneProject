package com.example.hungry_student_login.mainPage.restaurant;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.mainPage.restaurant.fragment.RestaurantInfoFragment;
import com.example.hungry_student_login.mainPage.restaurant.fragment.RestaurantReviewFragment;
import com.example.hungry_student_login.mainPage.restaurant.fragment.RestaurantmenuFragment;

public class RestaurantInfoPage extends AppCompatActivity {

    private final int menuFragment = 1;
    private final int infoFragment = 2;
    private final int reviewFragment = 3;

    FragmentManager fragmentManager = getSupportFragmentManager();

    RestaurantmenuFragment restaurantmenuFragment = new RestaurantmenuFragment();
    RestaurantInfoFragment restaurantInfoFragment = new RestaurantInfoFragment();
    RestaurantReviewFragment  restaurantReviewFragment = new RestaurantReviewFragment();


    ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.restaurant_info_fragment_container, restaurantmenuFragment).commit();


        findViewById(R.id.restaurant_menu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_info_fragment_container, restaurantmenuFragment).commit();
            }
        });
        findViewById(R.id.restaurant_info_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_info_fragment_container, restaurantInfoFragment).commit();

            }
        });
        findViewById(R.id.restaurant_review_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_info_fragment_container, restaurantReviewFragment).commit();
            }
        });
    }
}
