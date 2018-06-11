package com.example.android.assessmentgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

//    EditText email, password;
//    CheckBox showpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i = getIntent();
        Integer value = i.getIntExtra("Scene_Id",0);

//        email = (EditText) findViewById(R.id.emailId);
//        password = (EditText) findViewById(R.id.passw);
////        showpass = (CheckBox) findViewById(R.id.show_hide_password);
//        final Boolean status = showpass.isChecked();
//        showpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (status)
//                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                else
//                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            }
//        });
        Button letsgo = (Button) findViewById(R.id.letsgo);
        letsgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this,GameDetails.class);
                startActivity(it);

            }
        });

    }

    public void tosignup(View view) {
        Intent intent = new Intent(this,signup.class);
        startActivity(intent);
    }
}
