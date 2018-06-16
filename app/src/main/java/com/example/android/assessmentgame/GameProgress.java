package com.example.android.assessmentgame;

/**
 * Created by ritik on 6/15/2018.
 */

public class GameProgress {

    int sceneId;
    int storyId;



    public GameProgress(int storyId, int sceneId) {
        this.sceneId = sceneId;
        this.storyId = storyId;
    }




    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }
}
