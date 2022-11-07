package com.example.hungry_student_login.mainPage.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.RestaurantListData;

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

        nameText.setText(restaurantListData.getRestaurant_name());
        categoryText.setText(restaurantListData.categoryToString());
        rateText.setText(restaurantListData.getRateString());

        return view;
    }

    public void addItem(RestaurantListData restaurantListData) {
        items.add(restaurantListData);
    }
}
