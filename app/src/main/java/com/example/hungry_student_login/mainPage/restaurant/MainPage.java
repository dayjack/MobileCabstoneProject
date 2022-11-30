package com.example.hungry_student_login.mainPage.restaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.CategoryData;
import com.example.hungry_student_login.login.Login;
import com.example.hungry_student_login.mainPage.fragment.BoardFragment;
import com.example.hungry_student_login.mainPage.fragment.RestaurantListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainPage extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    Context context = this;

    /**
     * 바텁 탭 관련 변수들
     * */
    FragmentManager fragmentManager = getSupportFragmentManager();
    RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
    BoardFragment boardFragment = new BoardFragment();
    BottomNavigationView bottomNavigationView;
    TextView drawer_id;
    TextView drawer_email;
    MenuItem logout;

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView;
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.logout);
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
        editor = pref.edit();
        Boolean login = pref.getBoolean("login", false);

        if (login) {
            menuItem.setTitle("로그아웃");
        } else {
            menuItem.setTitle("로그인");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_48); //뒤로가기 버튼 이미지 지정


        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
        editor = pref.edit();
        Boolean login = pref.getBoolean("login", false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView;
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.logout);

        if (login) {
            menuItem.setTitle("로그아웃");
        } else {
            menuItem.setTitle("로그인");
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.logout){
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref;
                    SharedPreferences.Editor editor;
                    pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
                    editor = pref.edit();
                    Boolean login = pref.getBoolean("login", false);

                    if (login) {
                        editor.putString("id", null);
                        editor.putString("email", null);
                        editor.putString("nickname", null);
                        editor.putInt("auth", 0);
                        editor.putInt("scode", 0);
                        editor.putBoolean("login", false);
                        CategoryData.scode = 0;
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(MainPage.this, Login.class);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(MainPage.this, Login.class);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });





        /**
         * 바텀 탭 관련 기능들
         * **/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (CategoryData.mainpageFragment) {
            fragmentTransaction.replace(R.id.main_frame, restaurantListFragment).commit();
        } else {
            fragmentTransaction.replace(R.id.main_frame, boardFragment).commit();
        }


        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.restaurantlist_tab:
                        CategoryData.mainpageFragment = true;
                        fragmentTransaction.replace(R.id.main_frame, restaurantListFragment).commit();
                        break;
                    case R.id.board_tab:
                        CategoryData.mainpageFragment = false;
                        fragmentTransaction.replace(R.id.main_frame, boardFragment).commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                SharedPreferences pref;
                SharedPreferences.Editor editor;
                pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
                editor = pref.edit();
                /*scode = pref.getInt("scode", 0);*/


                drawer_id = findViewById(R.id.drawer_id);
                drawer_email = findViewById(R.id.drawer_email);



                drawer_id.setText( pref.getString("id", ""));
                drawer_email.setText( pref.getString("email", ""));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
