package com.moodcafe.assessmentgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        Button submit = findViewById(R.id.submit);
        final TextView ratingScale = findViewById(R.id.ratingScale);
        final EditText comment = findViewById(R.id.comment);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 0:
                        ratingBar.setRating(1);
                        ratingScale.setText("Very bad");
                        break;
                    case 1:
                        ratingScale.setText("Very bad");
                        break;
                    case 2:
                        ratingScale.setText("Need some improvement");
                        break;
                    case 3:
                        ratingScale.setText("Good");
                        break;
                    case 4:
                        ratingScale.setText("Great");
                        break;
                    case 5:
                        ratingScale.setText("Awesome. I love it");
                        break;
                    default:
                        ratingScale.setText("");

                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsOnline.connectedToInternet(getApplicationContext())) {
                    Toast.makeText(FeedbackActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    onBackPressed();
                    Toast.makeText(FeedbackActivity.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
                }
            }

    });
}
}
