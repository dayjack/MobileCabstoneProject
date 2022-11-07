package com.example.hungry_student_login.data;

import java.util.HashMap;
import java.util.Map;


public class RestaurantListData {

    int restaurant_id;
    int crn;

    public RestaurantListData() {
    }

    String restaurant_name;
    String email;
    String address;
    String restaurant_info;
    String menu;
    String food_img;
    int food_category;
    String hashtag;
    double rate_avg;
    int rate_total;
    int rate_count;
    int scode;


    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRestaurant_info(String restaurant_info) {
        this.restaurant_info = restaurant_info;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setFood_img(String food_img) {
        this.food_img = food_img;
    }

    public void setFood_category(int food_category) {
        this.food_category = food_category;
    }

    public void setRate_avg(double rate_avg) {
        this.rate_avg = rate_avg;
    }

    public void setRate_total(int rate_total) {
        this.rate_total = rate_total;
    }

    public void setRate_count(int rate_count) {
        this.rate_count = rate_count;
    }

    public void setScode(int scode) {
        this.scode = scode;
    }




    public int getRestaurant_id() {return this.restaurant_id; }
    public int getCrn() { return this.crn; }
    public String getRestaurant_name() { return this.restaurant_name; }
    public String getEmail() { return this.email; }
    public String getAddress() { return this.address; }
    public String getRestaurant_info() { return this.restaurant_info; }
    public String getMenu() { return this.menu; }
    public String getFood_img() { return this.food_img; }

    public String getRateString() {
        return Double.toString(this.rate_avg);
    }

    public int getRate_total() { return this.rate_total; }
    public int getRate_count() { return this.rate_count; }
    public int getScode() { return this.scode; }

    public RestaurantListData(int restaurant_id, int crn, String restaurant_name, String email, String address, String restaurant_info,
                              String menu, String food_img, int food_category, double rate_avg, int rate_total, int rate_count, int scode) {
        this.restaurant_id = restaurant_id;
        this.crn = crn;
        this.restaurant_name = restaurant_name;
        this.email = email;
        this.address = address;
        this.restaurant_info = restaurant_info;
        this.menu = menu;
        this.food_img = food_img;
        this.food_category = food_category;
        this.rate_avg = rate_avg;
        this.rate_total = rate_total;
        this.rate_count = rate_count;
        this.scode = scode;
    }



    static Map<Integer, String> categorymap = new HashMap<Integer, String>() {
        {
            put(1,"중식");
            put(2,"한식");
            put(3,"일식");
            put(4,"양식");
            put(5,"기타");
        }
    };

    public String categoryToString() {
        return categorymap.get(this.food_category);
    }


}
