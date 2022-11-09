package com.example.hungry_student_login.mainPage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.CategoryData;
import com.example.hungry_student_login.data.RestaurantListData;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantInfoPage;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends Fragment {



    String receiveMsg;
    ListView listView;
    RestaurantListAdapter adapter = new RestaurantListAdapter();
    int restaurant_id = 0;
    int scode = 0;
    String url = "http://43.206.19.165/2016041085" +
            "/restaurantlist.php?category="+CategoryData.category+"&restaurant_id="+restaurant_id+"&scode="+scode;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
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
        /**
         * 식당 리스트 페이지 카테고리 관련
         * **/
        View v = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        FrameLayout categoryFrame;
        categoryFrame = v.findViewById(R.id.category_frame);
        LayoutInflater categoryInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        categoryInflater.inflate(R.layout.category_gridlayout, categoryFrame, true);

        Button all = v.findViewById(R.id.all);
        Button chinese = v.findViewById(R.id.chinese);
        Button korean = v.findViewById(R.id.korean);
        Button japanese = v.findViewById(R.id.japanese);
        Button western = v.findViewById(R.id.western);
        Button etc = v.findViewById(R.id.ect);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryData.category = 0;
                refresh();
            }
        });
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryData.category = 1;
                refresh();
            }
        });
        korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryData.category = 2;
                refresh();
            }
        });
        japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryData.category = 3;
                refresh();
            }
        });
        western.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryData.category = 4;
                refresh();
            }
        });
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryData.category = 5;
                refresh();
            }
        });
        Log.d("tab", "refresh: "+url);
        listView = v.findViewById(R.id.restaurant_list);
        listView.setAdapter(adapter);

        new DownloadWebpageTask().execute(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), RestaurantInfoPage.class);
                startActivity(intent);
            }
        });
        return v;
    }
    private class DownloadWebpageTask extends AsyncTask<String,Void,String> {
        //주요 내용 실행
        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String)downloadUrl((String)urls[0]);
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
                while((line = bufreader.readLine()) != null) {
                    page += line;
                }
                Log.d("json", "downloadUrl: "+page);
                return page;
            } finally {
                conn.disconnect();
            }
        }
        //ui변경 작업 실행
        @Override
        protected void onPostExecute(String result) {
            try {
                Log.d("test", "onPostExecute: "+adapter.getCount());
                if (adapter.getCount() == 0) {
                    JSONArray jsonArray = new JSONArray(result);
                    if (jsonArray == null) {
                        Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                    }
                    for (int i=0; i < jsonArray.length(); i++) {
                        try {
                            Log.d("json", "try "+i);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            RestaurantListData temp = new RestaurantListData();

                            // Pulling items from the array
                            temp.setRestaurant_id(jsonObject.getInt("restaurant_id"));
                            temp.setCrn(jsonObject.getInt("crn"));
                            temp.setRestaurant_name(jsonObject.getString("restaurant_name"));
                            temp.setEmail(jsonObject.getString("email"));
                            temp.setAddress(jsonObject.getString("address"));
                            temp.setRestaurant_info(jsonObject.getString("restaurant_info"));
                            temp.setMenu(jsonObject.getString("menu"));
                            temp.setFood_img(jsonObject.getString("food_img"));
                            temp.setFood_category(jsonObject.getInt("food_category"));
                            temp.setHashtag(jsonObject.getString("hashtag"));
                            temp.setRate_avg(jsonObject.getDouble("rate_avg"));
                            temp.setRate_total(jsonObject.getInt("rate_total"));
                            temp.setRate_count(jsonObject.getInt("rate_count"));
                            temp.setScode(jsonObject.getInt("scode"));
                            //Log.d("json", temp.getRestaurant_name());

                            adapter.addItem(temp);
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        this.url = "http://43.206.19.165/2016041085" +
                "/restaurantlist.php?category="+CategoryData.category+"&restaurant_id="+restaurant_id+"&scode="+scode;
        adapter = new RestaurantListAdapter();
        new DownloadWebpageTask().execute(url);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }


}