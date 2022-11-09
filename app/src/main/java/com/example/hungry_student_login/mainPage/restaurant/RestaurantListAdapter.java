package com.example.hungry_student_login.mainPage.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.RestaurantListData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class RestaurantListAdapter extends BaseAdapter {

    ArrayList<RestaurantListData> items = new ArrayList<RestaurantListData>();
    Context context;

    @Override
    public String toString() {
        return "RestaurantListAdapter{" +
                "items=" + items +
                '}';
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        context = viewGroup.getContext();
        RestaurantListData restaurantListData = items.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.restaurant_list_item, viewGroup, false);
        }

        TextView nameText = view.findViewById(R.id.list_restaurant_name);
        TextView categoryText = view.findViewById(R.id.list_restaurant_category);
        TextView rateText = view.findViewById(R.id.list_restaurant_rate);
        ImageView imageView = view.findViewById(R.id.restaurant_thumbnail);




        String imgurl = "http://43.206.19.165";

        try {
            Log.d("img", "img: " + restaurantListData.getFood_img());
            JSONArray jsonArray = new JSONArray(restaurantListData.getFood_img());
            String temp = (String) jsonArray.get(0);
            Log.d("img", "temp: " + temp);
            String substringtemp = temp.substring(2);
            Log.d("img", "substringtemp: " + substringtemp);
            imgurl.concat(substringtemp);
            imgurl = "" + imgurl + substringtemp;
            Log.d("img", "imgurl: " + imgurl);
            Glide.with(view).load(imgurl).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameText.setText(restaurantListData.getRestaurant_name());
        categoryText.setText(restaurantListData.categoryToString());
        rateText.setText(restaurantListData.getRateString());

        return view;
    }

    public void addItem(RestaurantListData restaurantListData) {
        items.add(restaurantListData);
    }
}
