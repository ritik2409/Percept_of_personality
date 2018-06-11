package com.example.android.assessmentgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.icu.text.LocaleDisplayNames;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
//    LinearLayout linearLayout2, linearLayout3;
    ImageView other;
    TypeWriter otherText;
    ImageView protagonist;
    TypeWriter protagonistText;
    TypeWriter narratorText;
    TypeWriter pchoice1, pchoice2, ochoice1, ochoice2;
    int k = 0;
    int resourceId;
    String imagename;
    ArrayList<Content> content = new ArrayList<Content>();
    //    Content content.get(k);
    ArrayList<Story> story = new ArrayList<>();
    //    String[] dialogues, type;
//    Integer[] resources;
    String choices[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_begins);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        Integer value = intent.getIntExtra("Scene_Id", 0);
//        final String profession = intent.getStringExtra("Profession");
//        String gender = intent.getStringExtra("Gender");
        root = (RelativeLayout) findViewById(R.id.root);
//        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
//        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        other = (ImageView) findViewById(R.id.other_characters);
        otherText = (TypeWriter) findViewById(R.id.other_text);
        protagonist = (ImageView) findViewById(R.id.protagonist);
        protagonistText = (TypeWriter) findViewById(R.id.protagonist_text);
        narratorText = (TypeWriter) findViewById(R.id.narrator);

        pchoice1 = (TypeWriter) findViewById(R.id.pchoice1);
        pchoice2 = (TypeWriter) findViewById(R.id.pchoice2);

        ochoice1 = (TypeWriter) findViewById(R.id.ochoice1);
        ochoice2 = (TypeWriter) findViewById(R.id.ochoice2);
        choices = new String[2];


        convert();
//        dialogues = new String[content.size()];
//        type = new String[content.size()];
//        resources = new Integer[content.size()];
        activity();


//        root.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                removeall();
//                if (type[k] == "n") {
//                    narratorText.setText(dialogues[k]);
//                    narratorText.setBackgroundColor(Color.GREEN);
//                    root.setBackgroundResource(resources[k]);
//
//                } else if (type[k] == "l") {
//                    protagonistText.setText(dialogues[k]);
//                    protagonist.setImageResource(resources[k]);
//                    if (content.get(k).getIsQuestion() == true) {
//                        choices = content.get(k).getChoices();
//                        pchoice1.setText(choices[0]);
//                        pchoice2.setText(choices[1]);
//                    }
//
//                } else {
//                    otherText.setText(dialogues[k]);
//                    other.setImageResource(resources[k]);
//
//                    if (content.get(k).getIsQuestion() == true) {
//                        choices = content.get(k).getChoices();
//                        ochoice1.setText(choices[0]);
//                        ochoice2.setText(choices[1]);
//                    }
//
//                }
//                k++;
//
//                return false;
//            }
//        });
//
//
//    }

//    public void values() {
//
//
//        for (int i = 0; i < content.size(); i++) {
//            dialogues[i] = content.get(i).getDialogue();
//
//
//            if ((content.get(i).getNarrator()) == true) {
//                type[i] = "n";
//
//            } else if (content.get(i).getIsLeft() == true) {
//                type[i] = "l";
//
//            } else
//                type[i] = "r";
//
//            if (content.get(i).getBackgroundImageChange() == true) {
//                imagename = content.get(i).getBackgroundImage();
//                resourceId = this.getResources().getIdentifier(imagename, "drawable", this.getPackageName());
//                resources[i] = resourceId;
//            } else {
//                imagename = content.get(i).getMainCharacterImage();
//                resourceId = this.getResources().getIdentifier(imagename, "drawable", this.getPackageName());
//                resources[i] = resourceId;
//
//
//            }
//
//        }
////        Log.d("Dialogue 0", dialogues[0]);
//        Log.d("Dialogue 1", dialogues[1]);
//        Log.d("Dialogue 2", dialogues[2]);


    }


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
                }

                if (content.get(k).getNarrator() == true) {
                    narratorText.animateText(content.get(k).getDialogue());
                    narratorText.setVisibility(View.VISIBLE);
                }

                if (content.get(k).getIsLeft() == true) {
                    imagename = content.get(k).getMainCharacterImage();
                    resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
                    protagonist.setImageResource(resourceId);
                    protagonist.setVisibility(View.VISIBLE);
//                    protagonist.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.leftslide));
                    protagonistText.animateText(content.get(k).getDialogue());
                    protagonistText.setVisibility(View.VISIBLE);
                    Log.d("Dialogue is:",content.get(k).getDialogue());
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
                    otherText.animateText(content.get(k).getDialogue());
                    other.setVisibility(View.VISIBLE);
                    otherText.setVisibility(View.VISIBLE);
//                    other.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rightslide));

                    if (content.get(k).getIsQuestion() == true) {
                        choices = content.get(k).getChoices();
                        ochoice1.animateText(choices[0]);
                        ochoice2.animateText(choices[1]);
                        ochoice2.setVisibility(View.VISIBLE);
                        ochoice1.setVisibility(View.VISIBLE);
                    }


                }
                if (content.get(k).getIsQuestion() == false)
                    k = content.get(k).getNextId() - 1;


                return false;
            }
        });

    }

    private void removeall() {
        other.setVisibility(View.GONE);
        protagonist.setVisibility(View.GONE);
        narratorText.setVisibility(View.GONE);
        otherText.setVisibility(View.GONE);
//        other.clearAnimation();
//        protagonist.clearAnimation();
        protagonistText.setVisibility(View.GONE);
        pchoice1.setVisibility(View.GONE);
        pchoice2.setVisibility(View.GONE);
        ochoice2.setVisibility(View.GONE);
        ochoice1.setVisibility(View.GONE);
    }

    public String loadJSONFromAsset() {

        InputStream is = getResources().openRawResource(R.raw.data);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());

        }

        Log.d("json string", builder.toString());

        return builder.toString();


    }

    private void convert() {
        MainParser mainParser;
        try {
            Gson gson = new Gson();
            mainParser = gson.fromJson(loadJSONFromAsset(), MainParser.class);
            //story = new JSONObject(loadJSONFromAsset());
//             Log.d(" Story id is---",mainParser.getStory().getStoryId());
            story = mainParser.getStory();
//            Log.d("Story title is---",story.getStoryTitle());
            content = story.get(0).getContent();
//            Log.d("Dialogue 2",content.get(0).getMainCharacterImage());
//            Log.d("Questions", String.valueOf(content.get(1).getIsQuestion()));

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

//package com.example.android.assessmentgame;
//
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.graphics.Color;
//import android.icu.text.LocaleDisplayNames;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//public class GameBegins extends AppCompatActivity {
//    LinearLayout root;
//    ImageView other;
//    TextView otherText;
//    ImageView protagonist;
//    TextView protagonistText;
//    TextView narratorText;
//    TextView pchoice1, pchoice2, ochoice1, ochoice2;
//    int k = 0;
//    int resourceId;
//    String imagename;
//    ArrayList<Content> content = new ArrayList<>();
//    //    Content content.get(k);
//    Story story;
//    String choices[];
//    ScrollView svnarr;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_game_begins);
//        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("Name");
//        String profession = intent.getStringExtra("Profession");
//        String gender = intent.getStringExtra("Gender");
//        root = (LinearLayout) findViewById(R.id.root);
//        other = (ImageView) findViewById(R.id.other_characters);
//        otherText = (TextView) findViewById(R.id.other_text);
//        protagonist = (ImageView) findViewById(R.id.protagonist);
//        protagonistText = (TextView) findViewById(R.id.protagonist_text);
//        narratorText = (TextView) findViewById(R.id.narrator);
//
//        pchoice1 = (TextView) findViewById(R.id.pchoice1);
//        pchoice2 = (TextView) findViewById(R.id.pchoice2);
//
//        ochoice1 = (TextView) findViewById(R.id.ochoice1);
//        ochoice2 = (TextView) findViewById(R.id.ochoice2);
//        choices = new String[2];
//        svnarr = (ScrollView) findViewById(R.id.narratorscroll);
//
//
//        convert();
////        dialogues = new String[content.size()];
////        type = new String[content.size()];
////        resources = new Integer[content.size()];
//        activity();
//
//
////        root.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                removeall();
////                if (type[k] == "n") {
////                    narratorText.setText(dialogues[k]);
////                    narratorText.setBackgroundColor(Color.GREEN);
////                    root.setBackgroundResource(resources[k]);
////
////                } else if (type[k] == "l") {
////                    protagonistText.setText(dialogues[k]);
////                    protagonist.setImageResource(resources[k]);
////                    if (content.get(k).getIsQuestion() == true) {
////                        choices = content.get(k).getChoices();
////                        pchoice1.setText(choices[0]);
////                        pchoice2.setText(choices[1]);
////                    }
////
////                } else {
////                    otherText.setText(dialogues[k]);
////                    other.setImageResource(resources[k]);
////
////                    if (content.get(k).getIsQuestion() == true) {
////                        choices = content.get(k).getChoices();
////                        ochoice1.setText(choices[0]);
////                        ochoice2.setText(choices[1]);
////                    }
////
////                }
////                k++;
////
////                return false;
////            }
////        });
////
////
////    }
//
////    public void values() {
////
////
////        for (int i = 0; i < content.size(); i++) {
////            dialogues[i] = content.get(i).getDialogue();
////
////
////            if ((content.get(i).getNarrator()) == true) {
////                type[i] = "n";
////
////            } else if (content.get(i).getIsLeft() == true) {
////                type[i] = "l";
////
////            } else
////                type[i] = "r";
////
////            if (content.get(i).getBackgroundImageChange() == true) {
////                imagename = content.get(i).getBackgroundImage();
////                resourceId = this.getResources().getIdentifier(imagename, "drawable", this.getPackageName());
////                resources[i] = resourceId;
////            } else {
////                imagename = content.get(i).getMainCharacterImage();
////                resourceId = this.getResources().getIdentifier(imagename, "drawable", this.getPackageName());
////                resources[i] = resourceId;
////
////
////            }
////
////        }
//////        Log.d("Dialogue 0", dialogues[0]);
////        Log.d("Dialogue 1", dialogues[1]);
////        Log.d("Dialogue 2", dialogues[2]);
//
//
//    }
//
//
//    public void activity() {
//        root.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                removeall();
//
//                if (content.get(k).getBackgroundImageChange() == true) {
//                    imagename = content.get(k).getBackgroundImage();
//                    resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
//                    root.setBackgroundResource(resourceId);
//                }
//
//                if (content.get(k).getNarrator() == true) {
//                    narratorText.setText(content.get(k).getDialogue());
//                    svnarr.setVisibility(View.VISIBLE);
//                }
//
//                if (content.get(k).getIsLeft() == true) {
//                    imagename = content.get(k).getMainCharacterImage();
//                    resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
//                    protagonist.setImageResource(resourceId);
//                    protagonistText.setText(content.get(k).getDialogue());
//                    if (content.get(k).getIsQuestion() == true) {
//                        choices = content.get(k).getChoices();
//                        pchoice1.setText(choices[0]);
//                        pchoice1.setVisibility(View.VISIBLE);
//                        pchoice2.setVisibility(View.VISIBLE);
//                        pchoice2.setText(choices[1]);
//                    }
//
//
//                }
//
//                if (content.get(k).getIsRight() == true) {
//                    imagename = content.get(k).getMainCharacterImage();
//                    other.setImageResource(getResources().getIdentifier(imagename, "drawable", getPackageName()));
//                    otherText.setText(content.get(k).getDialogue());
//                    if (content.get(k).getIsQuestion() == true) {
//                        choices = content.get(k).getChoices();
//                        ochoice1.setText(choices[0]);
//                        ochoice2.setText(choices[1]);
//                        ochoice1.setVisibility(View.VISIBLE);
//                        ochoice2.setVisibility(View.VISIBLE);
//                    }
//
//                }
//                if(content.get(k).getIsQuestion()==false)
//                k = content.get(k).getNextId() - 1;
//
//
//                return false;
//            }
//        });
//
//    }
//
//    private void removeall() {
//        other.setImageDrawable(null);
//        otherText.setText(null);
//        protagonistText.setText(null);
//        protagonist.setImageDrawable(null);
//        svnarr.setVisibility(View.GONE);
//        pchoice1.setVisibility(View.GONE);
//        pchoice2.setVisibility(View.GONE);
//        ochoice1.setVisibility(View.GONE);
//        ochoice2.setVisibility(View.GONE);
//    }
//
//    public String loadJSONFromAsset() {
//
//        InputStream is = getResources().openRawResource(R.raw.data);
//        Scanner scanner = new Scanner(is);
//        StringBuilder builder = new StringBuilder();
//
//        while (scanner.hasNextLine()) {
//            builder.append(scanner.nextLine());
//
//        }
//
//        Log.d("json string", builder.toString());
//
//        return builder.toString();
//
//
//    }
//
//    private void convert() {
//        MainParser mainParser;
//        try {
//            Gson gson = new Gson();
//            mainParser = gson.fromJson(loadJSONFromAsset(), MainParser.class);
//            //story = new JSONObject(loadJSONFromAsset());
////             Log.d(" Story id is---",mainParser.getStory().getStoryId());
//            story = mainParser.getStory();
////            Log.d("Story title is---",story.getStoryTitle());
//            content = story.getContent();
////            Log.d("Dialogue 2",content.get(0).getMainCharacterImage());
////            Log.d("Questions", String.valueOf(content.get(1).getIsQuestion()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("gadbad hai", "bhai gadbad");
//        }
//
//
//    }
//
//
//    public void action(View view) {
//
//        if (view == pchoice1 || view == ochoice1) {
//            k = content.get(k).getGoWithOne() - 1;
//        } else {
//            k = content.get(k).getGoWithTwo() - 1;
//        }
//
//
//    }
//}
