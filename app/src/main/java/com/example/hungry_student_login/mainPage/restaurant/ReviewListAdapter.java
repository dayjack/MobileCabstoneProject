package com.example.hungry_student_login.mainPage.restaurant;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.ReviewData;

import java.util.ArrayList;

public class ReviewListAdapter extends BaseAdapter {

    ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();
    Context context;

    @Override
    public int getCount() {
        return reviewDataArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewDataArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        context = viewGroup.getContext();
        ReviewData reviewData = reviewDataArrayList.get(i);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.review_list_item, viewGroup, false);
        }
        TextView reviewContent = view.findViewById(R.id.review_content);
        TextView reviewTime = view.findViewById(R.id.review_time);
        TextView reviewNickname = view.findViewById(R.id.review_nickname);
        RatingBar reviewRating = view.findViewById(R.id.review_rate);

        reviewContent.setText(reviewData.getVcontent());
        Log.d("review", "getView: "+reviewData.getVcontent());
        reviewTime.setText(reviewData.getVtime());
        reviewNickname.setText(reviewData.getNickname());
        reviewRating.setRating(reviewData.getRate());

        return view;
    }

    public void addItem(ReviewData item) {
        reviewDataArrayList.add(item);
    }

}
