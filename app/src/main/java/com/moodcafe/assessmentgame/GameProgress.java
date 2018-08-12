package com.moodcafe.assessmentgame;

/**
 * Created by ritik on 6/15/2018.
 */

public class GameProgress {

    int sceneId;
    int storyId;
    int progress;
    int updatedBackground;
    String protagonistName;
    String storyTitle;

    public GameProgress(int storyId, int sceneId, int progress, int updatedBackground, String protagonistName, String storyTitle) {
        this.sceneId = sceneId;
        this.storyId = storyId;
        this.progress = progress;
        this.updatedBackground = updatedBackground;
        this.protagonistName = protagonistName;
        this.storyTitle = storyTitle;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getUpdatedBackground() {
        return updatedBackground;
    }

    public void setUpdatedBackground(int updatedBackground) {
        this.updatedBackground = updatedBackground;
    }

    public String getProtagonistName() {
        return protagonistName;
    }

    public void setProtagonistName(String protagonistName) {
        this.protagonistName = protagonistName;
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
