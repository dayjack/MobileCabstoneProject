package com.example.hungry_student_login.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hungry_student_login.R;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button reg_d = (Button) findViewById(R.id.register_detail);
        EditText editText = (EditText) findViewById(R.id.nSchoolCode);

        reg_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nSC = Integer.valueOf(editText.getText().toString());
                Intent intent = new Intent(Register.this, Register_detail_school.class);
                intent.putExtra("nSchoolCode", nSC);
                Log.d("REG", "onClick: "+nSC);
                startActivity(intent);
            }
        });




    }


}
