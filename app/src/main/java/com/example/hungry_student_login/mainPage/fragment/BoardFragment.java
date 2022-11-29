package com.example.hungry_student_login.mainPage.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Post;
import com.example.hungry_student_login.data.RestaurantListData;
import com.example.hungry_student_login.mainPage.Board.BoardWriteActivity;
import com.example.hungry_student_login.mainPage.Board.PostAdapter;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantInfoPage;
import com.example.hungry_student_login.mainPage.restaurant.RestaurantListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    RecyclerView postRecyclerView;
    PostAdapter postAdapter;
    List<Post> postList = new ArrayList<>();

    TextView free_board;
    ImageView create_icon;

    String url = "http://43.206.19.165/2016041085/postlist.php";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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

        View v = inflater.inflate(R.layout.fragment_board, container, false);
        postRecyclerView = v.findViewById(R.id.posted_writing);
//        free_board = v.findViewById(R.id.free_board);
        free_board = v.findViewById(R.id.free_board);
        create_icon = v.findViewById(R.id.create_icon);

        /**
         * create_icon -> BoardCreateFragment ???
         */
        create_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BoardWriteActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getContext().getSharedPreferences("user", Activity.MODE_PRIVATE);
        editor = pref.edit();
        int scode = pref.getInt("scode", 0);

        String url = "http://43.206.19.165/2016041085/postlist.php?scode=" + scode;
        Log.d("POSTLIST", "onCreateView: "+url);
        new DownloadPostTask().execute(url);

        return v;
    }


    private class DownloadPostTask extends AsyncTask<String, Void, String> {
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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            postAdapter = new PostAdapter();
            try {
                Log.d("post", "onPostExecute: "+postAdapter.getItemCount());
                Log.d("post", "onPostExecute: "+result);
                if (postAdapter.getItemCount() == 0) {
                    JSONArray jsonArray = new JSONArray(result);
                    if (jsonArray == null) {
                        Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                    }
                    postList = new ArrayList<>();
                    for (int i=0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            RestaurantListData temp = new RestaurantListData();
                            Post post = new Post();

                            post.setPnum(jsonObject.getInt("pnum"));
                            post.setNickname(jsonObject.getString("nickname"));
                            post.setPtitle(jsonObject.getString("ptitle"));
                            post.setPcontent(jsonObject.getString("pcontent"));
                            post.setPtime(jsonObject.getString("ptime"));
                            post.setScode(jsonObject.getInt("scode"));
                            postList.add(post);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("post", "postList:  " + postList.get(0).toString());
                    postAdapter.setPostList(postList);
                    Log.d("post", "postList:  " + postAdapter.getItemCount());
                    postRecyclerView.setAdapter(postAdapter);
                    postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    postAdapter.notifyDataSetChanged();

                    refresh();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }
}