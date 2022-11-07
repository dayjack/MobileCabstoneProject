package com.example.hungry_student_login.data;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class RestaurantListData {

    String name;
    int category;
    double rate;

    public String getName() {
        return this.name;
    }

    public String getRateString() {
        return Double.toString(this.rate);
    }

    public RestaurantListData(String name, int category, double rate) {
        this.name = name;
        this.category = category;
        this.rate = rate;
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
        return categorymap.get(this.category);
    }


}
