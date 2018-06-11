package com.example.android.assessmentgame;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ritik on 5/28/2018.
 */

public class Content {

    @SerializedName("isRight")
    @Expose
    private boolean isRight;


    @SerializedName("narrator")
    @Expose
    private boolean narrator;

    @SerializedName("sceneId")
    @Expose
    private int sceneId;

    @SerializedName("backgroundImageChange")
    @Expose
    private boolean backgroundImageChange;

    @SerializedName("dialogue")
    @Expose
    private String dialogue;

    @SerializedName("backgroundImage")
    @Expose
    private String backgroundImage;

    @SerializedName("isLeft")
    @Expose
    private boolean isLeft;

    @SerializedName("mainCharacterName")
    @Expose
    private String mainCharacterName;

    @SerializedName("mainCharacterImage")
    @Expose
    private String mainCharacterImage;

    @SerializedName("isQuestion")
    @Expose
    private boolean isQuestion;

    @SerializedName("choices")
    @Expose
    private String[] choices;

    @SerializedName("nextId")
    @Expose
    private int nextId;

    @SerializedName("goWithOne")
    @Expose
    private int goWithOne;

    @SerializedName("goWithTwo")
    @Expose
    private  int goWithTwo;

    public boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean getNarrator() {
        return narrator;
    }

    public void setNarrator(boolean narrator) {
        this.narrator = narrator;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public boolean getBackgroundImageChange() {
        return backgroundImageChange;
    }

    public void setBackgroundImageChange(boolean backgroundImageChange) {
        this.backgroundImageChange = backgroundImageChange;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public boolean getIsLeft() {
        return isLeft;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }


    public String getMainCharacterName() {
        return this.mainCharacterName;
    }

    public void setMainCharacterName(String mainCharacterName) {
        this.mainCharacterName = mainCharacterName;
    }


    public String getMainCharacterImage() {
        return this.mainCharacterImage;
    }

    public void setMainCharacterImage(String mainCharacterImage) {
        this.mainCharacterImage = mainCharacterImage;
    }


    public Boolean getIsQuestion() {
        return this.isQuestion;
    }

    public void setIsQuestion(Boolean isQuestion) {
        this.isQuestion = isQuestion;
    }


    public String[] getChoices() {
        return this.choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public int getGoWithOne() {
        return goWithOne;
    }

    public void setGoWithOne(int goWithOne) {
        this.goWithOne = goWithOne;
    }

    public int getGoWithTwo() {
        return goWithTwo;
    }

    public void setGoWithTwo(int goWithTwo) {
        this.goWithTwo = goWithTwo;
    }


//    @Override
//    public String toString() {
//        return "ClassPojo [isRight = " + isRight + ", narrator = " + narrator + ", sceneId = " + sceneId + ", backgroundImageChange = " + backgroundImageChange + ", dialogue2 = " + dialogue2 + ", backgroundImage = " + backgroundImage + ", isLeft = " + isLeft + "]";
//    }
}
