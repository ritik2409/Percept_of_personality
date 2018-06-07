package com.example.android.assessmentgame;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritik on 5/21/2018.
 */

public class MainParser {


    @SerializedName("story")
    @Expose
    private ArrayList<Story> story = new ArrayList<>();

    public ArrayList<Story> getStory() {
        return story;
    }

    public void setStory(ArrayList<Story> story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return "ClassPojo [story = " + story + "]";
    }


}
//    private String[] dialogues;
//    private Integer[] images;
//    private String[] scenes;
//    private String[] narrator;
//    private ArrayList<TextView> textViews;
//    private ArrayList<ImageView> imageViews;
//    private Integer[] background;
//    private Number[] changes;
//
//    public MainParser(String[] dialogues, Integer[] images, String[] scenes,
//                      String[] narrator, ArrayList<TextView> textViews,
//                      ArrayList<ImageView> imageViews, Integer[] background,
//                      Number[] changes) {
//        this.dialogues = dialogues;
//        this.images = images;
//        this.scenes = scenes;
//        this.narrator = narrator;
//        this.textViews = textViews;
//        this.imageViews = imageViews;
//        this.background = background;
//        this.changes = changes;
//    }
//
//    public String[] getDialogues() {
//        return dialogues;
//    }
//
//    public void setDialogues(String[] dialogues) {
//        this.dialogues = dialogues;
//    }
//
//    public Integer[] getImages() {
//        return images;
//    }
//
//    public void setImages(Integer[] images) {
//        this.images = images;
//    }
//
//    public String[] getScenes() {
//        return scenes;
//    }
//
//    public void setScenes(String[] scenes) {
//        this.scenes = scenes;
//    }
//
//    public String[] getNarrator() {
//        return narrator;
//    }
//
//    public void setNarrator(String[] narrator) {
//        this.narrator = narrator;
//    }
//
//    public ArrayList<TextView> getTextViews() {
//        return textViews;
//    }
//
//    public void setTextViews(ArrayList<TextView> textViews) {
//        this.textViews = textViews;
//    }
//
//    public ArrayList<ImageView> getImageViews() {
//        return imageViews;
//    }
//
//    public void setImageViews(ArrayList<ImageView> imageViews) {
//        this.imageViews = imageViews;
//    }
//
//    public Integer[] getBackground() {
//        return background;
//    }
//
//    public void setBackground(Integer[] background) {
//        this.background = background;
//    }
//
//    public Number[] getChanges() {
//        return changes;
//    }
//
//    public void setChanges(Number[] changes) {
//        this.changes = changes;
//    }

