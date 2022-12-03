package com.example.hungry_student_login.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hungry_student_login.data.Registration;
import com.example.hungry_student_login.mainPage.restaurant.MainPage;
import com.example.hungry_student_login.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {

    EditText userId;
    EditText pwd;
    String resultjson = "";
    boolean loginSuccess = false;
    String TAG = "LOGINFUNC";
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);
        userId = findViewById(R.id.userId);
        pwd = findViewById(R.id.pwd);
        mContext = this;

        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
        editor = pref.edit();
        loginSuccess = pref.getBoolean("login", false);

        if(loginSuccess){
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://43.206.19.165/2016041085/signin.php?id=" + userId.getText().toString() + "&password=" + pwd.getText().toString();
                new LoginTask().execute(url);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);

            }
        });
    }
    private class LoginTask extends AsyncTask<String, Void, String> {
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
                    if (jsonObject.getString("id").equals("")) {
                        return null;
                    }
                    Log.d(TAG, "downloadUrl: 실행" + resultjson);
                    Registration.id = jsonObject.getString("id");
                    Registration.auth = jsonObject.getInt("authority");
                    Registration.email = jsonObject.getString("email");
                    Registration.nickname = jsonObject.getString("nickname");
                    Registration.scode = jsonObject.getInt("scode");
                    /*CategoryData.scode = Registration.scode;*/

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
            if (Registration.id != null) {

                SharedPreferences pref;
                SharedPreferences.Editor editor;
                pref = getSharedPreferences("user", Activity.MODE_PRIVATE);
                editor = pref.edit();
                editor.putString("id", Registration.id);
                editor.putString("email", Registration.email);
                editor.putString("nickname", Registration.nickname);
                editor.putInt("auth", Registration.auth);
                editor.putInt("scode", Registration.scode);
                editor.putBoolean("login", true);
                editor.apply();
                editor.commit();
                Intent intent = new Intent(Login.this, MainPage.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_LONG).show();
            }
        }
    }


}