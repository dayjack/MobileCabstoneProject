package com.example.hungry_student_login.mainPage.Board;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.mainPage.fragment.BoardFragment;
import com.example.hungry_student_login.mainPage.restaurant.MainPage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BoardWriteActivity extends AppCompatActivity {

    ImageView createPost;
    EditText board_title;
    EditText board_content;
    ImageView returnPost;
    String TAG = "POSTWRITE";

    String url = "";

    String nickname = "text";
    int scode = 71074000;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_write_activity);

        createPost = findViewById(R.id.create_post_icon);
        board_title = findViewById(R.id.board_title);
        board_content = findViewById(R.id.board_content);
        returnPost = findViewById(R.id.post_write_return);
        returnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                startActivity(intent);
            }
        });


        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostCommit().execute();
            }
        });
    }

    private class PostCommit extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            SharedPreferences pref;
            SharedPreferences.Editor editor;
            pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
            editor = pref.edit();
            String nickname = pref.getString("nickname", "");
            scode = pref.getInt("scode", 0);
            Log.d(TAG, "doInBackground: " + nickname);

            String serverURL = "http://43.206.204.6/mobileProject/2016041085/writepost.php";
            String parameters = "nickname=" + nickname + "&ptitle=" + board_title.getText() + "&pcontent="
                    + board_content.getText() + "&scode=" + scode;
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
            board_title.setText("");
            board_content.setText("");
            Intent intent = new Intent(getApplicationContext(), MainPage.class);
            startActivity(intent);
        }
    }
}






