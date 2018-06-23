package com.example.android.assessmentgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.SparseIntArray;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ritik on 6/13/2018.
 */

public class ResumeManager {

    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    GameResumeParser parser;

    private static final String PREF_NAME = "myPref";

    private static final String GAME_RESUME = "IsPlayed";

    public static final String CURRENT_BACKGROUND = "current_background";

    public static final String Main_Character_Name = "main_character_name";


    public ResumeManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }


    public String initialiseJson(GameResumeParser parser) {

        Gson gson = new Gson();
        String json = gson.toJson(parser);
        editor.putString("MyData", json);
        editor.putBoolean(GAME_RESUME, true);
        editor.commit();
        return json;
    }


    public boolean isGameStarted() {
        return preferences.getBoolean(GAME_RESUME, false);
    }


    public void updateJson(int storyId, int sceneId) {
        int j;
        Gson gson = new Gson();
        String json = preferences.getString("MyData", "not found!");
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId) {
                gameProgresses.get(j).setSceneId(sceneId);
                break;
            }
        }
        String updatedJson = gson.toJson(gameResumeParser);
        editor.putString("MyData", updatedJson);
        editor.commit();
//      return gameProgresses.get(j).getSceneId();

    }

    public int getSceneId(int storyId) {
        int j;
        Gson gson = new Gson();
        String json = myData();
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId)
                break;

        }

        return gameProgresses.get(j).getSceneId();
    }

    public void clearGameProgress(int storyId) {
        updateJson(storyId, 0);


    }

    public void clearAllGame() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, GameDetails.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);

    }

    public void updateBackground(int storyId, int resourceId) {
        int j;
        Gson gson = new Gson();
        String json = preferences.getString("MyData", "not found!");
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId) {
                gameProgresses.get(j).setUpdatedBackground(resourceId);
                break;
            }
        }
        String updatedJson = gson.toJson(gameResumeParser);
        editor.putString("MyData", updatedJson);
        editor.commit();


    }

    public int getCurrentBackground(int storyId) {
        int j;
        Gson gson = new Gson();
        String json = myData();
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId)
                break;

        }

        return gameProgresses.get(j).getUpdatedBackground();

    }

    public String myData() {
        return preferences.getString("MyData", "Not Found");
    }

    public void updateProtagonist(int storyId, String protagonistName) {
        int j;
        Gson gson = new Gson();
        String json = myData();
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId) {
                gameProgresses.get(j).setProtagonistName(protagonistName);
                break;
            }
        }
        String updatedJson = gson.toJson(gameResumeParser);
        editor.putString("MyData", updatedJson);
        editor.commit();
    }

    public String getProtagonist(int storyId) {
        int j;
        Gson gson = new Gson();
        String json = myData();
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId)
                break;

        }

        return gameProgresses.get(j).getProtagonistName();


    }

    public void updateGameProgress(int storyId, int progress, int sceneId) {
        int j;
        Gson gson = new Gson();
        String json = myData();
//        updateJson(storyId,sceneId);
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId) {
                {
                    gameProgresses.get(j).setSceneId(sceneId);
                    gameProgresses.get(j).setProgress(progress);
                    break;
                }
            }
        }
        String jsonstring = gson.toJson(gameResumeParser);
        editor.putString("MyData", jsonstring);
        editor.commit();


    }

    public int getGameProgress(int storyId) {
        int j;
        Gson gson = new Gson();
        String json = myData();

        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId)
                break;

        }

        return gameProgresses.get(j).getProgress();


    }

}
