package com.example.hungry_student_login.mainPage.restaurant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.hungry_student_login.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class RestaurnatImgPagerAdapter extends PagerAdapter {

    private static final String TAG = "imgTag";
    private Context context;
    int count;
    ArrayList<String> urls = new ArrayList<>();

    public RestaurnatImgPagerAdapter(Context context) {
        this.context = context;
    }

    public RestaurnatImgPagerAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    public RestaurnatImgPagerAdapter(Context context, int count, ArrayList<String> urls) {
        this.context = context;
        this.count = count;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (View) object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.info_imageview, container, false);
        ImageView imageView = view.findViewById(R.id.img_pager);
        String url = "http://43.206.19.165";
        String temp = (String) urls.get(position);
        String substringtemp = temp.substring(2);
        url.concat(substringtemp);
        url = "" + url + substringtemp;
        Log.d(TAG, "instantiateItem: " + url);
        Glide.with(context).load(url).into(imageView);
        container.addView(view);
        return view;
    }


}
