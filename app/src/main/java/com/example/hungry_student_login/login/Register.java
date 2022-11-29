package com.example.hungry_student_login.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hungry_student_login.R;
import com.example.hungry_student_login.mainPage.restaurant.MainPage;

public class Register extends AppCompatActivity {
    RadioGroup radioGroup;
    int auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button reg_d = (Button) findViewById(R.id.register_detail);
        EditText editText = (EditText) findViewById(R.id.nSchoolCode);
        Button code = (Button) findViewById(R.id.codeSearch);
        radioGroup = findViewById(R.id.radio_group);
        ImageView back = findViewById(R.id.register_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.sUser:
                        auth = 0;
                        break;
                    case R.id.nUser:
                        auth = 1;
                        break;
                }
            }
        });

        reg_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nSC = Integer.valueOf(editText.getText().toString());
                if (auth == 0) {
                    Intent intent = new Intent(Register.this, Register_detail_school.class);
                    intent.putExtra("nSchoolCode", nSC);
                    Log.d("REG", "onClick: "+nSC);
                    Log.d("auth", String.valueOf(auth));
                    startActivity(intent);
                } else if (auth == 1) {
                    Intent intent = new Intent(Register.this, Register_detail_normal.class);
                    intent.putExtra("nSchoolCode", nSC);
                    Log.d("REG", "onClick: "+nSC);
                    Log.d("auth", String.valueOf(auth));
                    startActivity(intent);
                }

            }
        });
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, WebViewActivity.class);
                startActivity(intent);
            }
        });




    }


}
