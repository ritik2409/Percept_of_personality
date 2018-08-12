package com.moodcafe.assessmentgame;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends NavigationDrawer {
    RelativeLayout root;
    RelativeLayout rl1, rl2;
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
    int previous_id = 0;
    int questionType = 0;

    ArrayList<Story> story = new ArrayList<>();
    MainParser mainParser;
    ResumeManager rm;
    AnalysisDataManager analysisDataManager;
    ProgressDialog progressDialog;

    String choices[];
    int i = 0;
    String dataFile;
    Integer value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        value = intent.getIntExtra("Story_id", 1);
        dataFile = intent.getStringExtra("Json_data");
        rm = new ResumeManager(this);
        analysisDataManager = new AnalysisDataManager(this);
        analysisDataManager.initialiseAnalysis(value + 1);

        progressDialog = new ProgressDialog(this);
        convert();


        root = (RelativeLayout) findViewById(R.id.root);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
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
        ImageButton imageButton = findViewById(R.id.back);
        //redirecting to main activity on back arrow clicked
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //to check whether the user is playing for the first time if not then getting the last updated background
        k = rm.getSceneId(value + 1);
        if (k != 0) {
            root.setBackgroundResource(rm.getCurrentBackground(value + 1));
        }

        removeall();
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                addContentToScreen();
                return false;
            }
        });

    }


    //refer docs : for more understanding this function refer story.json file
    private void addContentToScreen() {

        //removing last sceneId content from the screen
        removeall();
        //checking of there is a background update in the current sceneID
        if (content.get(k).getBackgroundImageChange() == true) {
            imagename = content.get(k).getBackgroundImage();
            resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
            root.setBackgroundResource(resourceId);
            //storing the latest background in the app
            rm.updateBackground(value + 1, resourceId);
        }

        //checking if there is a narrator in the current sceneID
        if (content.get(k).getNarrator() == true) {
            narratorText.animateText(content.get(k).getDialogue());
            narratorText.setVisibility(View.VISIBLE);
        }

        //to check whether it is the protagonist dialogue and then showing it to the left of the screen
        if (content.get(k).getIsLeft() == true) {
            imagename = content.get(k).getMainCharacterImage();
            resourceId = getResources().getIdentifier(imagename, "drawable", getPackageName());
            protagonist.setImageResource(resourceId);
            protagonistText.setText(content.get(k).getDialogue());
            protagonist_name.setText(rm.getProtagonist(value + 1));
            someChanges(protagonistText, content.get(k).getDialogue());
            protagonistText.setVisibility(View.VISIBLE);
            protagonist.setVisibility(View.VISIBLE);
            protagonist_name.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            relativeParams.setMargins(0, 0, 0, 120);  // left, top, right, bottom
            rl1.setLayoutParams(relativeParams);
            rl1.requestLayout();
            if (content.get(k).getIsQuestion() == true) {//checking for choices in the question
                choices = content.get(k).getChoices();
                pchoice1.setVisibility(View.VISIBLE);
                pchoice2.setVisibility(View.VISIBLE);
                pchoice1.animateText(choices[0]);
                pchoice2.animateText(choices[1]);
                questionType = content.get(k).getQuestionType();
                relativeParams.setMargins(0, 0, 0, 0);  // left, top, right, bottom
                rl1.setLayoutParams(relativeParams);
                rl1.requestLayout();

            }

        }

        //to check whether it is the other character's dialogue and then showing it to the right of the screen
        if (content.get(k).getIsRight() == true) {
            imagename = content.get(k).getMainCharacterImage();
            other.setImageResource(getResources().getIdentifier(imagename, "drawable", getPackageName()));
            otherText.setText(content.get(k).getDialogue());
            other_name.setText(content.get(k).getMainCharacterName());
            someChanges(otherText, content.get(k).getDialogue());
            other.setVisibility(View.VISIBLE);
            otherText.setVisibility(View.VISIBLE);
            other_name.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
            relativeParams.setMargins(0, 0, 0, 120);  // left, top, right, bottom
            rl2.setLayoutParams(relativeParams);
            rl2.requestLayout();

            if (content.get(k).getIsQuestion() == true) {
                choices = content.get(k).getChoices();
                ochoice1.animateText(choices[0]);
                ochoice2.animateText(choices[1]);
                ochoice2.setVisibility(View.VISIBLE);
                ochoice1.setVisibility(View.VISIBLE);
                questionType = content.get(k).getQuestionType();
                relativeParams.setMargins(0, 0, 0, 0);  // left, top, right, bottom
                rl2.setLayoutParams(relativeParams);
                rl2.requestLayout();

            }


        }

        //to check whether it is 9th type of question, if then showing it in a different way
        if (content.get(k).getIsQuestion() == true) {
            questionType = content.get(k).getQuestionType();
            if (questionType == 9) {
                choices = content.get(k).getChoices();
                //displaying the validity question
                displayQuestion(content.get(k).getDialogue(), choices[0], choices[1]);

                previous_id = k;
                k = content.get(k).getNextId() - 1;
            }

        }

        //getting the next scene id if there is no question at the screen, if there is then accessing the next sceneId differently
        if (content.get(k).getIsQuestion() == false) {

            previous_id = k;
            k = content.get(k).getNextId() - 1;
        }
        update();


    }

    //display validity question
    private void displayQuestion(final String question, String option1, String option2) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Validity Question")
                .setMessage(question)
                .setCancelable(false)
                .setPositiveButton(option1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        analysisDataManager.updateChoice(value + 1, content.get(k).getQuestionId() + String.valueOf(1), questionType);
                        dialogInterface.dismiss();
                        addContentToScreen();
                    }
                })
                .setNegativeButton(option2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        analysisDataManager.updateChoice(value + 1, content.get(k).getQuestionId() + String.valueOf(2), questionType);
                        dialogInterface.dismiss();
                        addContentToScreen();
                    }
                })
                .show();


    }

    //updating current sceneId to shared preferences(memory)
    public void update() {
        int size = content.size(); //story size
        int current = previous_id + 1;
        int progress = (int) (((float) current / size) * 100);

        rm.updateGameProgress(value + 1, progress, previous_id);//updating

        //if story is complete then redirecting to result activity
        if (progress == 100) {
            analysisDataManager.finalResult(value + 1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);//showing a dialogue box regarding story completion
            builder.setTitle("Completed")
                    .setMessage("You just completed a story! Press OK to get your analysis.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            progressDialog.setTitle("Analysing...");
                            progressDialog.show();
//                            updateToServer();
                            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                            intent.putExtra("Story Id", value + 1);
//                            progressDialog.cancel();
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {//if cancelled redirecting to main activity
                            dialogInterface.dismiss();
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();


        }
    }

    //updating the whole analysis of the user to the sercer
    private void updateToServer() {
        String URL = "http://139.59.64.113/api/game/user/data/store";
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> hashMap;
        hashMap = sessionManager.getUserDetails();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        String time = new SimpleDateFormat("yyyy-MMM-d HH:mm:ss").format(Calendar.getInstance().getTime());
        String json = analysisDataManager.getAnalysisJson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            jsonObject.put("Timestamp", time);
            if (sessionManager.isLoggedIn())
                jsonObject.put("UniqueId", hashMap.get(SessionManager.KEY_EMAIL));
            else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }jsonObject.put("UniqueId", telephonyManager.getDeviceId());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //handling if any error in making the request
                        progressDialog.cancel();
                        Toast.makeText(GameActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

        ) {//getting the headers : content_type : json
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;

            }

        };

        requestQueue.add(request);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void someChanges(final TypeWriter tw, final String dialogue) {
        tw.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = tw.getLineCount(); // getting the line count
                // Use lineCount here
                if (lineCount > 3) {
                    int lineEndIndex = tw.getLayout().getLineEnd(2);
                    String text = tw.getText().subSequence(0, lineEndIndex - 3) + "..."; // getting the text f first 3 lines
                    tw.animateText(text);


                    for (i = lineEndIndex - 1; i >= 0; i--) {
                        char ch = text.charAt(i);
                        if (ch == ' ')
                            break;
                    }

                    String text1 = (String) dialogue.subSequence(i + 1, dialogue.length()); //storing the remaining dialogue
                    content.get(previous_id).setDialogue(text1);
                    k = previous_id;

                } else {
                    tw.animateText(dialogue);
                }


            }
        });


    }

    //removing all the content of previousID from the screen
    private void removeall() {
        other.setVisibility(View.GONE);
        protagonist.setVisibility(View.GONE);
        narratorText.setVisibility(View.GONE);
        otherText.setVisibility(View.GONE);
        other_name.setVisibility(View.GONE);
        protagonist_name.setVisibility(View.GONE);
        protagonistText.setVisibility(View.GONE);
        pchoice1.setVisibility(View.GONE);
        pchoice2.setVisibility(View.GONE);
        ochoice2.setVisibility(View.GONE);
        ochoice1.setVisibility(View.GONE);
    }

    //refer docs
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

    //handling choice choosen by the user
    public void action(View view) {

        if (view == pchoice1 || view == ochoice1) {
            analysisDataManager.updateChoice(value + 1, content.get(k).getQuestionId() + String.valueOf(1), questionType);
            k = content.get(k).getNextId() - 1;
        } else {
            analysisDataManager.updateChoice(value + 1, content.get(k).getQuestionId() + String.valueOf(2), questionType);
            k = content.get(k).getNextId() - 1;
        }
        addContentToScreen();

    }
}

