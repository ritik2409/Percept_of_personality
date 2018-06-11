package com.example.android.assessmentgame;

import android.content.Intent;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class DummyActivity extends AppCompatActivity {
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        final Intent intent = getIntent();
        Integer value = intent.getIntExtra("Scene_Id", 10);

        final TextView typeWriter = (TextView) findViewById(R.id.text4);
        typeWriter.setText("Hey what the hell do you want to do with my name. Just go back! You understand you idiot.");
//        String rest = (String) typeWriter.getText().subSequence(typeWriter.getLayout().getEllipsisStart(0), typeWriter.getText().length());
//        Log.d("Rest of the text", rest);


        final TextView textView = (TextView) findViewById(R.id.text5);
        final TextView textView1 = (TextView) findViewById(R.id.text6);
//        textView.setText(typeWriter.getText().subSequence(typeWriter.getLayout().getEllipsisStart(0),typeWriter.getText().length()));
//        ImageView img = (ImageView) findViewById(R.id.img);

//        img.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.leftslide));
//        typeWriter.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                typeWriter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                Log.i("test" ,"EllipsisText="+getEllipsisText(typeWriter));
//            }
//        });


        boolean post = typeWriter.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = typeWriter.getLineCount();
                // Use lineCount here
                if (lineCount > 1) {
                    int lineEndIndex = typeWriter.getLayout().getLineEnd(0);
                    String text = typeWriter.getText().subSequence(0, lineEndIndex - 3) + "...";
                    textView.setText(text);

                    for (i = lineEndIndex - 1; i >= 0; i--) {
                        char ch = text.charAt(i);
                        if (ch == ' ')
                            break;
                    }

                    String text1 = (String) typeWriter.getText().subSequence(i + 1, typeWriter.getText().length());
                    textView1.setText(text1);
                }

            }
        });


    }

//    public static String getEllipsisText(TextView textView) {
//        // test that we have a textview and it has text
//        if (textView==null || TextUtils.isEmpty(textView.getText())) return null;
//        Layout l = textView.getLayout();
//        if (l!=null) {
//            // find the last visible position
//            int end = l.getLineEnd(textView.getMaxLines()-1);
//            // get only the text after that position
//            return textView.getText().toString().substring(end);
//        }
//
//        return null;
//    }
}

