package com.moodcafe.assessmentgame;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ritik on 5/28/2018.
 */

public class Story {

    @SerializedName("content")
    @Expose
    private ArrayList<Content> content = new ArrayList<>();

    @SerializedName("storyId")
    @Expose
    private int storyId;

    @SerializedName("storyTitle")
    @Expose
    private String storyTitle;

    @SerializedName("storyCover")
    @Expose
    private String storyCover;

    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTiltle(String storyTiltle) {
        this.storyTitle = storyTiltle;
    }

    @Override
    public String toString() {
        return "ClassPojo [content = " + content + ", storyId = " + storyId + ", storyTiltle = " + storyTitle + "]";
    }


    public String getStoryCover() {
        return storyCover;
    }

    public void setStoryCover(String storyCover) {
        this.storyCover = storyCover;
    }
}
