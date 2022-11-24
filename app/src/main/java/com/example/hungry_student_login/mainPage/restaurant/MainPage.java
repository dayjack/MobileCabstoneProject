package com.example.hungry_student_login.mainPage.restaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.mainPage.fragment.BoardFragment;
import com.example.hungry_student_login.mainPage.fragment.RestaurantListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {

    MenuItem mSearch;

    /**
     * 바텁 탭 관련 변수들
     * */
    FragmentManager fragmentManager = getSupportFragmentManager();
    RestaurantListFragment restaurantListFragment = new RestaurantListFragment();
    BoardFragment boardFragment = new BoardFragment();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        /**
         * 바텀 탭 관련 기능들
         * **/
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, restaurantListFragment).commit();

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.restaurantlist_tab:
                        fragmentTransaction.replace(R.id.main_frame, restaurantListFragment).commit();
                        break;
                    case R.id.board_tab:
                        fragmentTransaction.replace(R.id.main_frame, boardFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
    /**
    * 검색 바 관련
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.list_menu, menu);

        mSearch = menu.findItem(R.id.search);

        mSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Log.d("[Search]","현재 상태 : 확장됨");
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.d("[Search]","현재 상태 : 축소됨");
                return false;
            }
        });
        //menuItem을 이용해서 SearchView 변수 생성
        SearchView sv=(SearchView)mSearch.getActionView();
        //확인버튼 활성화
        sv.setSubmitButtonEnabled(true);

        //SearchView의 검색 이벤트
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //검색버튼을 눌렀을 경우
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*TextView text = (TextView)findViewById(R.id.txtresult);
                text.setText(query + "를 검색합니다.");*/
                return true;
            }

            //텍스트가 바뀔때마다 호출
            @Override
            public boolean onQueryTextChange(String newText) {
          /*      TextView text = (TextView)findViewById(R.id.txtsearch);
                text.setText("검색식 : "+newText);*/
                return true;
            }
        });
        return true;
    }
}
