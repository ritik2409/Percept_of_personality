package com.example.android.assessmentgame;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritik on 5/28/2018.
 */

public class Story {

    @SerializedName("content")
    @Expose
    private ArrayList<Content> content = new ArrayList<>();

    @SerializedName("storyId")
    @Expose
    private String storyId;

    @SerializedName("storyTitle")
    @Expose
    private String storyTitle;

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
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


}
