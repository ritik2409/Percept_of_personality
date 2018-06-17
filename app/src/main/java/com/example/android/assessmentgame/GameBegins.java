package com.example.android.assessmentgame;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.LocaleDisplayNames;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameBegins extends AppCompatActivity {
    RelativeLayout root;
    ImageView other;
    TypeWriter otherText;
    ImageView protagonist;
    TypeWriter protagonistText;
    TypeWriter narratorText;
    TypeWriter pchoice1, pchoice2, ochoice1, ochoice2;
    TextView protagonist_name, other_name;
    int k = 0;
    int resourceId;
    String imagename;
    ArrayList<Content> content = new ArrayList<Content>();
    int previous_id;
//    Button play,restart;

    ArrayList<Story> story = new ArrayList<>();
    MainParser mainParser;
    ResumeManager rm;

    String choices[];
    int i = 0;
    int continue_text = 0;
    String dataFile;
    Integer value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game_begins);
//        getActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        value = intent.getIntExtra("Scene_Id", 0);
        dataFile = intent.getStringExtra("Json_data");
        rm = new ResumeManager(getApplicationContext());
        convert();


        root = (RelativeLayout) findViewById(R.id.root);
        other = (ImageView) findViewById(R.id.other_characters);
        otherText = (TypeWriter) findViewById(R.id.other_text);
        protagonist = (ImageView) findViewById(R.id.protagonist);
        protagonistText = (TypeWriter) findViewById(R.id.protagonist_text);
        narratorText = (TypeWriter) findViewById(R.id.narrator);
        protagonist_name = findViewById(R.id.protagonist_name);
        other_name = findViewById(R.id.other_name);

        pchoice1 = (TypeWriter) findViewById(R.id.pchoice1);
        pchoice2 = (TypeWriter) findViewById(R.id.pchoice2);

        ochoice1 = (TypeWriter) findViewById(R.id.ochoice1);
        ochoice2 = (TypeWriter) findViewById(R.id.ochoice2);
        choices = new String[2];
//        play = (Button) findViewById(R.id.continue_game);
//        restart = (Button) findViewById(R.id.restart_game);

//        if (rm.getSceneId(1) != 0)
//            askResume();
        k = rm.getSceneId(value + 1);
        if (k != 0) {
            root.setBackgroundResource(rm.getCurrentBackground(value + 1));
        }

        activity();


    }

//    public void askResume() {
//
//        final Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.resume_dialog);
//        dialog.findViewById(R.id.continue_game).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                k = rm.getSceneId(1);
//                root.setBackgroundResource(rm.getCurrentBackground());
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.restart_game).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rm.clearGameProgress(1);
//                dialog.dismiss();
//            }
//        });
//        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                Intent intent = new Intent(GameBegins.this, GameDetails.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
////        int progress = rm.getSceneId(1) / content.size() * 100;
//
////        ProgressBar progressBar = findViewById(R.id.progress);
////        progressBar.setProgress(progress);
//
//
//        dialog.show();
//
//
//    }


    public void activity() {
        removeall();
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                removeall();
                if (content.get(k).getBackgroundImageChange() == true) {
                    imagename = content.get(k).getBackgroundImage();
                    resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
                    root.setBackgroundResource(resourceId);
                    rm.updateBackground(value + 1, resourceId);
                }

                if (content.get(k).getNarrator() == true) {
                    narratorText.animateText(content.get(k).getDialogue());
                    narratorText.setVisibility(View.VISIBLE);
                }

                if (content.get(k).getIsLeft() == true) {
                    imagename = content.get(k).getMainCharacterImage();
                    resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
                    protagonist.setImageResource(resourceId);
//                    protagonist.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.leftslide));
                    protagonistText.setText(content.get(k).getDialogue());
                    protagonist_name.setText(rm.getProtagonist(value+1));
                    someChanges(protagonistText, content.get(k).getDialogue());
                    protagonistText.setVisibility(View.VISIBLE);
                    protagonist.setVisibility(View.VISIBLE);
                    protagonist_name.setVisibility(View.VISIBLE);
                    Log.d("Dialogue is:", content.get(k).getDialogue());
                    if (content.get(k).getIsQuestion() == true) {
                        choices = content.get(k).getChoices();
                        pchoice1.setVisibility(View.VISIBLE);
                        pchoice2.setVisibility(View.VISIBLE);
                        pchoice1.animateText(choices[0]);
                        pchoice2.animateText(choices[1]);
                    }

                }

                if (content.get(k).getIsRight() == true) {
                    imagename = content.get(k).getMainCharacterImage();
                    other.setImageResource(getResources().getIdentifier(imagename, "drawable", getPackageName()));
                    otherText.setText(content.get(k).getDialogue());
                    other_name.setText(content.get(k).getMainCharacterName());
                    someChanges(otherText, content.get(k).getDialogue());
                    other.setVisibility(View.VISIBLE);
                    otherText.setVisibility(View.VISIBLE);
                    other_name.setVisibility(View.VISIBLE);
//                    other.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rightslide));

                    if (content.get(k).getIsQuestion() == true) {
                        choices = content.get(k).getChoices();
                        ochoice1.animateText(choices[0]);
                        ochoice2.animateText(choices[1]);
                        ochoice2.setVisibility(View.VISIBLE);
                        ochoice1.setVisibility(View.VISIBLE);
                    }


                }
                if (content.get(k).getIsQuestion() == false) {

                    previous_id = k;
                    k = content.get(k).getNextId() - 1;
                }
                rm.updateJson(1, k);


                return false;
            }
        });

    }

    @Override
    public void onDestroy() {
        rm.updateJson(1, k);
        super.onDestroy();
    }

    public void someChanges(final TypeWriter tw, final String dialogue) {
        tw.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = tw.getLineCount();
                // Use lineCount here
                if (lineCount > 3) {
                    int lineEndIndex = tw.getLayout().getLineEnd(2);
                    String text = tw.getText().subSequence(0, lineEndIndex - 3) + "...";
                    tw.animateText(text);


                    for (i = lineEndIndex - 1; i >= 0; i--) {
                        char ch = text.charAt(i);
                        if (ch == ' ')
                            break;
                    }

                    String text1 = (String) dialogue.subSequence(i + 1, dialogue.length());
                    content.get(previous_id).setDialogue(text1);
                    k = previous_id;

                } else {
                    tw.animateText(dialogue);
                }


            }
        });


    }

    private void removeall() {
        other.setVisibility(View.GONE);
        protagonist.setVisibility(View.GONE);
        narratorText.setVisibility(View.GONE);
        otherText.setVisibility(View.GONE);
        other_name.setVisibility(View.GONE);
        protagonist_name.setVisibility(View.GONE);
//        other.clearAnimation();
//        protagonist.clearAnimation();
        protagonistText.setVisibility(View.GONE);
        pchoice1.setVisibility(View.GONE);
        pchoice2.setVisibility(View.GONE);
        ochoice2.setVisibility(View.GONE);
        ochoice1.setVisibility(View.GONE);
    }


    private void convert() {
        try {
            Gson gson = new Gson();
            mainParser = gson.fromJson(dataFile, MainParser.class);
            story = mainParser.getStory();
            content = story.get(value).getContent();


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("gadbad hai", "bhai gadbad");
        }


    }

    public void action(View view) {

        if (view == pchoice1 || view == ochoice1) {
            k = content.get(k).getGoWithOne() - 1;
        } else {
            k = content.get(k).getGoWithTwo() - 1;
        }


    }
}

