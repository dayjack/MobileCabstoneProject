package com.example.hungry_student_login.login;

import android.content.Intent;
import android.os.Bundle;
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

public class Register_detail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_detail);

        Button reg = (Button) findViewById(R.id.registration);
        EditText eId = (EditText) findViewById(R.id.iD);
        EditText eNick = (EditText) findViewById(R.id.nickName);
        EditText eEmail = (EditText) findViewById(R.id.schoolEmail);
        EditText ePwd = (EditText) findViewById(R.id.pwd);
        EditText eRepwd = (EditText) findViewById(R.id.repwd);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = eId.getText().toString();
                String nick = eNick.getText().toString();
                String email = eEmail.getText().toString();
                String pwd = ePwd.getText().toString();
                String repwd = eRepwd.getText().toString();
            }
        });
    }
}
