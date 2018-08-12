package com.moodcafe.assessmentgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends NavigationDrawer {

    RecyclerView rv;
    ChooseGameAdapter adapter;
    String[] storyHeader;
    Integer[] drawable;
    MainParser mainParser;
    ArrayList<Story> story = new ArrayList<>();
    String dataFile;
    ResumeManager manager;
    ProgressDialog progressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDrawer(R.layout.activity_game_details);

        sessionManager = new SessionManager(this);
        /**
         * Check login status
         * If false it will redirect user to login page
         * Else won't do anything
         */
        if (!((sessionManager.isLoggedIn()) ||(sessionManager.isLoginSkipped())) ) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, LoginActivity.class);
            // Starting Login Activity
            this.startActivity(i);
            finish();


        }


        progressDialog = new ProgressDialog(this);
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        convert();
        manager = new ResumeManager(this);
        if (!manager.isGameStarted())
            jsonInitialise();


        adapter = new ChooseGameAdapter(this, drawable, R.layout.story_card, dataFile);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);


    }

    public void askResume(final int position) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.resume_dialog);
        ImageView imageView = dialog.findViewById(R.id.storyCover);
        imageView.setImageResource(drawable[position]);
        TextView textView = dialog.findViewById(R.id.storyHeader);
        textView.setText(storyHeader[position]);
        ImageButton ib = dialog.findViewById(R.id.description);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo();
            }
        });
        final EditText editText = dialog.findViewById(R.id.character_name);
        final ProgressBar progressBar = dialog.findViewById(R.id.progress);
        TextView progressValue = dialog.findViewById(R.id.progressValue);

        //handling actions of resume button
        dialog.findViewById(R.id.continue_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager.getSceneId(position + 1) == 0) {
                    if (!TextUtils.isEmpty(editText.getText().toString())) {
                        manager.updateProtagonist(position + 1, editText.getText().toString());
                        progressDialog.setTitle("Resuming Game...");
                        progressDialog.show();
                        dialog.dismiss();
                        startGame(position);
                    } else
                        editText.setError("Protagonist name can't be blank!");
                } else startGame(position);


            }
        });

        //To change the setup of the dialog box if the user is playing for the first time
        if (manager.getSceneId(position + 1) == 0) {
            dialog.findViewById(R.id.restart_game).setVisibility(View.GONE);
            TextView textView1 = dialog.findViewById(R.id.continue_game);
            textView1.setText("Play");
            progressBar.setVisibility(View.GONE);
            progressValue.setVisibility(View.GONE);
        } else {
            editText.setVisibility(View.GONE);
            progressBar.setProgress(manager.getGameProgress(position + 1));
            progressValue.setText(String.valueOf(manager.getGameProgress(position + 1)) + "%");
        }

        //To show "completed" word in the progress bar of the game if the user already completed the game
        if (manager.getSceneId(position + 1) + 1 == story.get(position).getContent().size()) {
            progressValue.setText("Completed");
            dialog.findViewById(R.id.continue_game).setVisibility(View.GONE);
            TextView textView2 = dialog.findViewById(R.id.restart_game);
            textView2.setText("Play Again");

        }

        //Handling the action of restart button
        dialog.findViewById(R.id.restart_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Loading Game...");
                progressDialog.show();
                manager.clearGameProgress(position + 1);
                startGame(position);
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    //To show the description of the story on clicking the info button
    public void showInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Description")
                .setMessage("Reimagine your choices with the story of a professional in their struggle to maintain a healthy work-life balance.")
                .setCancelable(true)
                .show();

    }

    //TO start Game Activity from the resume game dialog
    public void startGame(int position) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        //attaching the storyId for the Game Activity to understand
        intent.putExtra("Story_id", position);
        //attaching the game json data file
        intent.putExtra("Json_data", dataFile);
        progressDialog.cancel();
        startActivity(intent);


    }

    //To parse the game json data : story.json to respective classes and getting the images and story title to show at the screen
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

    //To convert json file to a string to parse json string to the class easily
    public String loadJSONFromAsset() {

        InputStream is = getResources().openRawResource(R.raw.story);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());

        }

        Log.d("json string", builder.toString());

        return builder.toString();


    }

    //To initialise the resume json string with each story to have zero progress , zero scene id , living room background, protagonist name, story ID and respective story title
    public void jsonInitialise() {

        ArrayList<GameProgress> arrayList = new ArrayList<>();

        GameProgress gameProgress;

        for (int i = 0; i < story.size(); i++) {
            gameProgress = new GameProgress(story.get(i).getStoryId(), 0, 0, R.drawable.living_room, "Protagonist", story.get(i).getStoryTitle());
            arrayList.add(gameProgress);

        }

        GameResumeParser parser = new GameResumeParser(arrayList);
        manager.initialiseJson(parser);

    }
}



