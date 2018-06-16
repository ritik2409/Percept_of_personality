package com.example.android.assessmentgame;

import android.content.Context;
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


    public void updateJson(int i, int i1) {
        int j;
        Gson gson = new Gson();
        String json = preferences.getString("MyData", "not found!");
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == i) {
                gameProgresses.get(j).setSceneId(i1);
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
        int j;
        Gson gson = new Gson();
        String json = myData();
        GameResumeParser gameResumeParser = gson.fromJson(json, GameResumeParser.class);
        ArrayList<GameProgress> gameProgresses = gameResumeParser.getValues();
        for (j = 0; j < gameProgresses.size(); j++) {
            if (gameProgresses.get(j).getStoryId() == storyId)
                gameProgresses.get(j).setSceneId(0);

        }


    }

    public void clearAllGame() {
        editor.clear();
        editor.commit();
    }

    public void updateBackgroud(int resourceId) {
        editor.putInt(CURRENT_BACKGROUND, resourceId);
        editor.commit();


    }

    public int getCurrentBackground() {
        return preferences.getInt(CURRENT_BACKGROUND, 0);
    }

    public String myData() {
        return preferences.getString("MyData","Not Found");
    }

}
