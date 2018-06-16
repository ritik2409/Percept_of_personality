package com.example.android.assessmentgame;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class GameDetails extends AppCompatActivity {

    RecyclerView rv;
    ChooseGameAdapter adapter;
    String[] storyHeader;
    Integer[] drawable;
    MainParser mainParser;
    ArrayList<Story> story = new ArrayList<>();
    String dataFile;
    ResumeManager manager;
    ArrayList<ProgressBar> progressBars;

//    private EditText name_text;
//    private Spinner gender_spinner;
//    private Spinner prof_spinner;
//    private ArrayAdapter genderAdapter;
//    private ArrayAdapter profAdapter;
//    private String gender;
//    private String name;
//    private String profession;
//    private Button btn;
//    int gender_pos, prof_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);
        progressBars = new ArrayList<>();
//        loadGameProgress();


        rv = (RecyclerView) findViewById(R.id.recycler_view);
        convert();
        manager = new ResumeManager(getApplicationContext());
        if (!manager.isGameStarted())
            jsonInitialise();


        adapter = new ChooseGameAdapter(this, storyHeader, drawable, R.layout.story_card, dataFile);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.VERTICAL));
        rv.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.HORIZONTAL));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);




    }

    public void askResume(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.resume_dialog);
        dialog.findViewById(R.id.continue_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                k = manager.getSceneId(1);
//                root.setBackgroundResource(rm.getCurrentBackground());
                Intent intent = new Intent(GameDetails.this,GameBegins.class);
                intent.putExtra("Scene_Id",position);
                intent.putExtra("Json_data",dataFile);

                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.restart_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.clearGameProgress(1);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

//        int progress = rm.getSceneId(1) / content.size() * 100;

//        ProgressBar progressBar = findViewById(R.id.progress);
//        progressBar.setProgress(progress);


        dialog.show();


    }

//    private void loadGameProgress() {
//        String data = manager.myData();
//        Gson gson = new Gson();
//        GameResumeParser parser = gson.fromJson(data, GameResumeParser.class);
//        ArrayList<GameProgress> gameProgresses = new ArrayList<>();
//        gameProgresses = parser.getValues();
//        ProgressBar progressBar = new ProgressBar(this);
//        for (int i = 0; i < gameProgresses.size(); i++) {
//            progressBar.setProgress(gameProgresses.get(i).getProgress());
//            progressBars.add(progressBar);
//        }
//
//
//    }


    private void convert() {
        try {
            Gson gson = new Gson();
            dataFile = loadJSONFromAsset();
            mainParser = gson.fromJson(dataFile, MainParser.class);
            story = mainParser.getStory();
            storyHeader = new String[story.size()];
            drawable = new Integer[story.size()];
            int resource;
            String imagename;
            for (int i = 0; i < story.size(); i++) {
                storyHeader[i] = story.get(i).getStoryTitle();
                imagename = story.get(i).getStoryCover();
                resource = getResources().getIdentifier(imagename, "drawable", getPackageName());
                drawable[i] = resource;

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("gadbad hai", "bhai gadbad");
        }


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

    public void jsonInitialise() {

        ArrayList<GameProgress> arrayList = new ArrayList<>();

        GameProgress gameProgress;

        for (int i = 0; i < story.size(); i++) {
            gameProgress = new GameProgress(story.get(i).getStoryId(), 0);
            arrayList.add(gameProgress);

        }

        GameResumeParser parser = new GameResumeParser(arrayList);
        manager.initialiseJson(parser);

    }
}


//        name_text = (EditText) findViewById(R.id.name);
//        gender_spinner = (Spinner) findViewById(R.id.gender);
//        prof_spinner = (Spinner) findViewById(R.id.profession);
//
//        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
//        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        gender_spinner.setAdapter(genderAdapter);
//
//
//        profAdapter = ArrayAdapter.createFromResource(this, R.array.profession, android.R.layout.simple_spinner_item);
//        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        prof_spinner.setAdapter(profAdapter);
//
//
//        btn = (Button) findViewById(R.id.submit);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = name_text.getText().toString();
//                gender = gender_spinner.getSelectedItem().toString();
//                profession = prof_spinner.getSelectedItem().toString();
//                gender_pos = gender_spinner.getSelectedItemPosition();
//                prof_pos = prof_spinner.getSelectedItemPosition();
//
//
//                if ((TextUtils.isEmpty(name) || (gender_pos == 0) || (prof_pos == 0))) {
//                    Toast.makeText(GameDetails.this, "Please fill all the details!", Toast.LENGTH_LONG).show();
//
//                } else {
//                    Intent i = new Intent(GameDetails.this, GameBegins.class);
//                    i.putExtra("Gender", gender);
//                    i.putExtra("Profession", profession);
//                    i.putExtra("Name", name);
//                    startActivity(i);
//
//                }
//
//
//            }
//        });
//
//
