package com.example.hungry_student_login.mainPage.Board;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Post;
import com.example.hungry_student_login.data.RestaurantListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostActicity extends AppCompatActivity {

    TextView ptitle;
    TextView nickname;
    TextView pdate;
    TextView pcontent;
    String resultjson;
    Post post = new Post();
    int pnum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        ptitle = findViewById(R.id.post_tittle);
        nickname = findViewById(R.id.post_nickname);
        pdate = findViewById(R.id.post_date);
        pcontent = findViewById(R.id.post_content);
        Intent intent = getIntent();
        pnum = intent.getIntExtra("pnum", 0);
        String url = "http://43.206.19.165/2016041085/postlist.php?pnum=" + pnum;
        new DownloadPostTask().execute(url);

    }
    private class DownloadPostTask extends AsyncTask<String, Void, String> {
        //주요 내용 실행
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
                Log.d("infotag", "downloadUrl: " + page);
                resultjson = page;

                try {
                    JSONArray jsonArray = new JSONArray(resultjson);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    post.setPnum(jsonObject.getInt("pnum"));
                    post.setNickname(jsonObject.getString("nickname"));
                    post.setPtitle(jsonObject.getString("ptitle"));
                    post.setPcontent(jsonObject.getString("pcontent"));
                    post.setPtime(jsonObject.getString("ptime"));
                    post.setScode(jsonObject.getInt("scode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } finally {
                conn.disconnect();
            }
            return resultjson;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ptitle.setText(post.getPtitle());
            nickname.setText(post.getNickname());
            pdate.setText(post.getPtime());
            pcontent.setText(post.getPcontent());

        }
    }
}
