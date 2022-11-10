package com.example.hungry_student_login.mainPage.restaurant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.CategoryData;
import com.example.hungry_student_login.data.RestaurantListData;
import com.example.hungry_student_login.mainPage.restaurant.fragment.RestaurantInfoFragment;
import com.example.hungry_student_login.mainPage.restaurant.fragment.RestaurantReviewFragment;
import com.example.hungry_student_login.mainPage.restaurant.fragment.RestaurantmenuFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestaurantInfoPage extends AppCompatActivity {

    private final int menuFragment = 1;
    private final int infoFragment = 2;
    private final int reviewFragment = 3;

    FragmentManager fragmentManager = getSupportFragmentManager();

    RestaurantmenuFragment restaurantmenuFragment = new RestaurantmenuFragment();
    RestaurantInfoFragment restaurantInfoFragment = new RestaurantInfoFragment();
    RestaurantReviewFragment restaurantReviewFragment = new RestaurantReviewFragment();

    TextView restaurantNameText;
    RatingBar ratingBar;

    String receiveMsg;
    ListView listView;
    String resultjson;

    RestaurantListData restaurantListData = new RestaurantListData();

    int restaurant_id = 0;
    int scode = 0;

    ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        findViewById(R.id.restaurant_menu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("menu", restaurantListData.getMenu());
                restaurantmenuFragment.setArguments(bundle);
                transaction.replace(R.id.restaurant_info_fragment_container, restaurantmenuFragment).commit();
            }
        });
        findViewById(R.id.restaurant_info_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("info", restaurantListData.getRestaurant_info());
                restaurantInfoFragment.setArguments(bundle);
                transaction.replace(R.id.restaurant_info_fragment_container, restaurantInfoFragment).commit();
            }
        });
        findViewById(R.id.restaurant_review_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.restaurant_info_fragment_container, restaurantReviewFragment).commit();
            }
        });

        Intent intent = getIntent();
        restaurant_id = intent.getIntExtra("restaurant_id", 0);
        String url = "http://43.206.19.165/2016041085/restaurantlist.php?restaurant_id=" + restaurant_id + "&scode=" + scode;
        Log.d("infotag", "onCreate: "+url);
        restaurantNameText = findViewById(R.id.restaurant_name);
        ratingBar = findViewById(R.id.restaurant_rating);
        new DownloadWebpageTask().execute(url);


    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        //주요 내용 실행
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.d("infotag", "doInBackground: ");
                return (String) downloadUrl((String) urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        private String downloadUrl(String myurl) throws IOException {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }
                Log.d("infotag", "downloadUrl: " + page);
                resultjson = page;

                try {
                    JSONArray jsonArray = new JSONArray(resultjson);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    Log.d("infotag", "jsonObject: " + jsonObject.getInt("restaurant_id"));

                    // Pulling items from the array
                    restaurantListData.setRestaurant_id(jsonObject.getInt("restaurant_id"));
                    restaurantListData.setCrn(jsonObject.getInt("crn"));
                    restaurantListData.setRestaurant_name(jsonObject.getString("restaurant_name"));
                    restaurantListData.setEmail(jsonObject.getString("email"));
                    restaurantListData.setAddress(jsonObject.getString("address"));
                    restaurantListData.setRestaurant_info(jsonObject.getString("restaurant_info"));
                    restaurantListData.setMenu(jsonObject.getString("menu"));
                    restaurantListData.setFood_img(jsonObject.getString("food_img"));
                    restaurantListData.setFood_category(jsonObject.getInt("food_category"));
                    restaurantListData.setHashtag(jsonObject.getString("hashtag"));
                    restaurantListData.setRate_avg(jsonObject.getDouble("rate_avg"));
                    restaurantListData.setRate_total(jsonObject.getInt("rate_total"));
                    restaurantListData.setRate_count(jsonObject.getInt("rate_count"));
                    restaurantListData.setScode(jsonObject.getInt("scode"));
                    Log.d("infotag", restaurantListData.getRestaurant_name());
                    Log.d("infotag", "toString: "+restaurantListData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return page;
            } finally {
                conn.disconnect();
            }
        }

        //ui변경 작업 실행
        @Override
        protected void onPostExecute(String result) {
            Log.d("infotag", "onPostExecute: "+restaurantListData.getRestaurant_name());
            restaurantNameText.setText(restaurantListData.getRestaurant_name());
            ratingBar.setRating((float) restaurantListData.getRate_avg());
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("menu", restaurantListData.getMenu());
            restaurantmenuFragment.setArguments(bundle);
            transaction.replace(R.id.restaurant_info_fragment_container, restaurantmenuFragment).commit();
        }
    }
}
