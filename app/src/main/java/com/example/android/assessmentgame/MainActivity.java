package com.example.android.assessmentgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent i = new Intent(MainActivity.this, GameDetails.class);
//        startActivity(i);
        textView = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);

    }

    public void change(View view) {
        textView.setText("Welcome to the java world!");
        textView.setVisibility(View.VISIBLE);

    }
}
