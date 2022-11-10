package com.example.hungry_student_login.mainPage.restaurant.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.CategoryData;
import com.example.hungry_student_login.data.ReviewData;
import com.example.hungry_student_login.mainPage.restaurant.ReviewListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listView;
    ReviewListAdapter adapter;

    private static final String TAG = "review";

    int restaurant_id = 0;
    String url = "http://43.206.19.165/2016041085/reviewlist.php?restaurant_id=";


    public RestaurantReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantReviewFragment newInstance(String param1, String param2) {
        RestaurantReviewFragment fragment = new RestaurantReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_restaurant_review, container, false);
        listView = v.findViewById(R.id.restaurant_review_list);
        adapter = new ReviewListAdapter();

        Bundle bundle = getArguments();
        CategoryData.restaurant_id = bundle.getInt("review");

        url = "http://43.206.19.165/2016041085/reviewlist.php?restaurant_id=" + CategoryData.restaurant_id;


        Log.d(TAG, "url : " + url);

        new DownloadReviewPage().execute(url);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return v;
    }

    private class DownloadReviewPage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
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
                return page;
            } finally {
                conn.disconnect();
            }
        }

        //ui변경 작업 실행
        @Override
        protected void onPostExecute(String result) {
            try {
                Log.d("review", "onPostExecute: "+result);
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ReviewData temp = new ReviewData();
                        temp.setVcode(jsonObject.getInt("vcode"));
                        temp.setRestaurant_id(jsonObject.getInt("restaurant_id"));
                        temp.setVtime(jsonObject.getString("vtime"));
                        temp.setVcontent(jsonObject.getString("vcontent"));
                        temp.setRate((float) jsonObject.getDouble("rate"));
                        temp.setNickname(jsonObject.getString("nickname"));
                        adapter.addItem(temp);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    }
}
