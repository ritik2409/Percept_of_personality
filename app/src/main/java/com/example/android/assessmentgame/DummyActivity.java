package com.example.android.assessmentgame;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DummyActivity extends NavigationDrawer {
    //    int i = 0;
//    TextView textView;
//    ResumeManager resumeManager;
//    int k;
    int value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.fragment_contact);



    }
}


//        typeWriter.setText(++0++"0+Hey what the hell do you want to do with my name. Just go back! You understand you idiot.");
//        String rest = (String) typeWriter.getText().subSequence(typeWriter.getLayout().getEllipsisStart(0), typeWriter.getText().length());
//        Log.d("Rest of the text", rest);


//        final TextView textView = (TextView) findViewById(R.id.text5);
//        final TextView textView1 = (TextView) findViewById(R.id.text6);
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


//        boolean post = typeWriter.post(new Runnable() {
//            @Override
//            public void run() {
//                int lineCount = typeWriter.getLineCount();
//                // Use lineCount here
//                if (lineCount > 1) {
//                    int lineEndIndex = typeWriter.getLayout().getLineEnd(0);
//                    String text = typeWriter.getText().subSequence(0, lineEndIndex - 3) + "...";
//                    textView.setText(text);
//
//                    for (i = lineEndIndex - 1; i >= 0; i--) {
//                        char ch = text.charAt(i);
//                        if (ch == ' ')
//                            break;
//                    }
//
//                    String text1 = (String) typeWriter.getText().subSequence(i + 1, typeWriter.getText().length());
//                    textView1.setText(text1);
//                }
//
//            }
//        });
//



//    @Override
//    protected void onPause() {
//        resumeManager.updateJson(value+1, 69);
//        super.onPause();
//    }

//    @Override
//    protected void onDestroy() {
//        resumeManager.updateJson(value+1, 69);
//
//
//
//
//
//        super.onDestroy();
//    }
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




