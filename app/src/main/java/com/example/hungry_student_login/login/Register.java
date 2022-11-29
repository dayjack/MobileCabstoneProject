package com.example.hungry_student_login.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hungry_student_login.R;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button reg_d = (Button) findViewById(R.id.register_detail);
        EditText editText = (EditText) findViewById(R.id.schoolName);

        reg_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Register_detail.class);
                startActivity(intent);
            }
        });

        //학교 선택페이지 날릴지 말지지
        //안날림 뒤에 ac.kr로 할거임
    }


}
