package com.example.hungry_student_login.mainPage.restaurant.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Toast;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

    EditText editContent;
    RatingBar ratingBar;
    Button button;
    ScrollView scrollView;

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

        editContent = v.findViewById(R.id.review_content_commit);
        ratingBar = v.findViewById(R.id.review_rate_commit);
        button = v.findViewById(R.id.review_commit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("commit", "onClick: "+"버튼 툴림");
                new ReviewCommit().execute("http://43.206.19.165/2016041085/writereview.php");

            }
        });



        return v;
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ReviewListAdapter listAdapter = (ReviewListAdapter) listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();
            Log.d("list", "setListViewHeightBasedOnItems: " + numberOfItems);
            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            Log.d("list", "setListViewHeightBasedOnItems: " + params.height);
            listView.setLayoutParams(params);
            listView.requestLayout();
            //setDynamicHeight(listView);
            return true;

        } else {
            return false;
        }
    }



    private class ReviewCommit extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String serverURL = "http://43.206.19.165/2016041085/writereview.php";

            String parameters = "restaurant_id=" + CategoryData.restaurant_id +
                    "&vcontent=" + editContent.getText().toString() + "&rate=" + ratingBar.getRating()+"&nickname=nickname";
            Log.d("commit", "doInBackground: " + parameters);

            HttpURLConnection conn;
            try {
                URL text = new URL(serverURL);
                conn = (HttpURLConnection) text.openConnection();


                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("POST");
                conn.connect();

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(parameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = conn.getResponseCode();
                Log.d("commit", "status : " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = conn.getInputStream();
                } else {
                    inputStream = conn.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                Log.d("commit", "doInBackground: " + sb.toString());
                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            editContent.setText("");
            ratingBar.setRating(0);
            Toast.makeText(getContext(), "등록되었습니다.", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
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
                setListViewHeightBasedOnItems(listView);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    }
}
