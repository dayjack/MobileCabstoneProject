package com.example.hungry_student_login.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Registration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Register_detail_normal extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_detail_normal);

        Button reg = (Button) findViewById(R.id.nRegistration);
        EditText eId = (EditText) findViewById(R.id.nId);
        EditText eNick = (EditText) findViewById(R.id.nNickName);
        EditText eEmail = (EditText) findViewById(R.id.nEmail);
        EditText ePwd = (EditText) findViewById(R.id.nPwd);
        EditText eRepwd = (EditText) findViewById(R.id.nRepwd);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = eId.getText().toString();
                String nick = eNick.getText().toString();
                String email = eEmail.getText().toString();
                String pwd = ePwd.getText().toString();
                String repwd = eRepwd.getText().toString();

                try {
                    JSONObject jsonObject = new JSONObject();
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register_detail_normal.this, Login.class);
                        startActivity(intent);
                    } else if (id == null) {
                        Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT);
                        return;
                    } else if (nick == null) {
                        Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT);
                        return;
                    } else if (email == null) {
                        Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT);
                        return;
                    } else if (pwd == null) {
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT);
                        return;
                    } else if (repwd == null) {
                        Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT);
                        return;
                    } else if (pwd != repwd) {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new UploadRegTask().execute();
            }
        });
    }
    private class UploadRegTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = "http://43.206.19.165/2016041085/signup.php";
            String parameters = "id=" + Registration.id + "&password=" + Registration.pwd +
                    "&email=" + Registration.email + ".ac.kr&scode=" + Registration.scode +
                    "&nickname=" + Registration.nickname + "&authority=" + Registration.auth;
            HttpURLConnection conn;

            try {
                URL text = new URL(url);
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
                Log.d("comit", "status : " + responseStatusCode);

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
                Log.d("comit", "doInBackground : " + sb.toString());
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}
