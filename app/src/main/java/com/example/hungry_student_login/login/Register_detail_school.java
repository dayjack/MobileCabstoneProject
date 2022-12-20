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
import com.example.hungry_student_login.mainPage.restaurant.MainPage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Register_detail_school extends AppCompatActivity {

    Button reg;
    EditText eId;
    EditText eNick;
    EditText eEmail;
    EditText ePwd;
    EditText eRepwd;
    EditText verify;
    Button verifyBtn;
    Button verifyEmail;
    TextView verifyText;
    String id;
    String nick;
    String email;
    String pwd;
    String repwd;

    int emailNum;
    int randomNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_detail_school);


        reg = (Button) findViewById(R.id.registration);
        eId = (EditText) findViewById(R.id.iD);
        eNick = (EditText) findViewById(R.id.nickName);
        eEmail = (EditText) findViewById(R.id.schoolEmail);
        ePwd = (EditText) findViewById(R.id.pwd);
        eRepwd = (EditText) findViewById(R.id.repwd);
        verify = findViewById(R.id.verify);
        verifyBtn = findViewById(R.id.verifybtn);
        verifyEmail = findViewById(R.id.verifyemail);
        verifyText = findViewById(R.id.verifytext);
        ImageView back = findViewById(R.id.register_detail_school_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_detail_school.this, Register.class);
                startActivity(intent);
            }
        });


        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailNum = Integer.valueOf(verify.getText().toString());
                if (emailNum == randomNum) {
                    verifyText.setText("인증 완료 되었습니다.");
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Email 발송", Toast.LENGTH_SHORT).show();
                new EmailTask().execute();
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = eId.getText().toString();
                nick = eNick.getText().toString();
                email = eEmail.getText().toString();
                pwd = ePwd.getText().toString();
                repwd = eRepwd.getText().toString();

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
                } else if (!verifyText.getText().toString().equals("인증 완료 되었습니다.")) {
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


            Intent intent = getIntent();
            int scode = intent.getIntExtra("nSchoolCode", 0);
            Log.d("REG", "onCreate: " + scode);


            String url = "http://43.206.204.6/mobileProject/2016041085/signup.php";
            String parameters = "id=" + id + "&password=" + pwd +
                    "&email=" + email + ".ac.kr&scode=" + scode +
                    "&nickname=" + nick + "&authority=" + 0;
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
            Intent intent = new Intent(Register_detail_school.this, Login.class);
            startActivity(intent);
        }
    }

    private class EmailTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String email = eEmail.getText().toString();
            email = email + ".ac.kr";

            String url = "http://43.206.204.6/mobileProject/2016041085/email.php";
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
