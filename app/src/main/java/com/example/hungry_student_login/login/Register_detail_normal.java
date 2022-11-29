package com.example.hungry_student_login.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.data.Registration;
import com.example.hungry_student_login.mainPage.restaurant.MainPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Register_detail_normal extends AppCompatActivity {

    Button reg;
    EditText eId;
    EditText eNick;
    EditText eEmail;
    EditText ePwd;
    EditText eRepwd;

    EditText nverify;

    Button nVerifyBtn;
    TextView nVerifyText;


    int randomNum;
    int emailNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_detail_normal);


        reg = (Button) findViewById(R.id.nRegistration);
        eId = (EditText) findViewById(R.id.nId);
        eNick = (EditText) findViewById(R.id.nNickName);
        eEmail = (EditText) findViewById(R.id.nEmail);
        ePwd = (EditText) findViewById(R.id.nPwd);
        eRepwd = (EditText) findViewById(R.id.nRepwd);
        nverify = findViewById(R.id.nverify);
        nVerifyBtn = findViewById(R.id.nVerifybtn);
        nVerifyText = findViewById(R.id.nverifytext);
        ImageView back = findViewById(R.id.register_detail_normal_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_detail_normal.this, Register.class);
                startActivity(intent);
            }
        });

        nVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EmailTask().execute();
            }
        });

        nverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailNum = Integer.valueOf(nverify.getText().toString());
                if (emailNum == randomNum) {
                    nVerifyText.setText("인증 완료 되었습니다.");
                }
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = eId.getText().toString();
                String nick = eNick.getText().toString();
                String email = eEmail.getText().toString();
                String pwd = ePwd.getText().toString();
                String repwd = eRepwd.getText().toString();

                if (id.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (nick.equals("")) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (pwd.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (repwd.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!pwd.equals(repwd)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (!nVerifyText.getText().toString().equals("인증 완료 되었습니다.")) {
                    Toast.makeText(getApplicationContext(), "이메일 인증이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    new UploadRegTask().execute();
                }
            }
        });
    }

    private class UploadRegTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String id = eId.getText().toString();
            String nick = eNick.getText().toString();
            String email = eEmail.getText().toString();
            String pwd = ePwd.getText().toString();
            String repwd = eRepwd.getText().toString();
            Intent intent = getIntent();
            int scode = intent.getIntExtra("nSchoolCode", 0);
            Log.d("REG", "onCreate: " + scode);

            String url = "http://43.206.19.165/2016041085/signup.php";
            String parameters = "id=" + id + "&password=" + pwd +
                    "&email=" + email + "&scode=" + scode +
                    "&nickname=" + nick + "&authority=" + 1;
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
            Intent intent = new Intent(Register_detail_normal.this, Login.class);
            startActivity(intent);
        }
    }

    private class EmailTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String email = eEmail.getText().toString();

            String url = "http://43.206.19.165/2016041085/email.php";
            double dValue = Math.random();
            randomNum = (int)(dValue * 10000);
            Log.d("EMAIL", "doInBackground: " + randomNum);

            String parameters = "email=" + email + "&num=" + randomNum;
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
