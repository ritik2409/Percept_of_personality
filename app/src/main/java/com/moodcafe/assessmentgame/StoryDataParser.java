package com.moodcafe.assessmentgame;

import java.util.ArrayList;

/**
 * Created by ritik on 7/2/2018.
 */

public class StoryDataParser {
    int storyId;
    String[] Qt1 = new String[5];
    String[] Qt2 = new String[5];
    String[] Qt3 = new String[5];
    String[] Qt4 = new String[5];
    String[] Qt5 = new String[5];
    String[] Qt6 = new String[5];
    String[] Qt7 = new String[5];
    String[] Qt8 = new String[5];
    String[] Qt9 = new String[5];

    public StoryDataParser(int storyId, String[] qt1, String[] qt2, String[] qt3, String[] qt4, String[] qt5, String[] qt6, String[] qt7, String[] qt8, String[] qt9) {
        this.storyId = storyId;
        Qt1 = qt1;
        Qt2 = qt2;
        Qt3 = qt3;
        Qt4 = qt4;
        Qt5 = qt5;
        Qt6 = qt6;
        Qt7 = qt7;
        Qt8 = qt8;
        Qt9 = qt9;
    }

    public String[] getQt1() {
        return Qt1;
    }

    public void setQt1(String[] qt1) {
        Qt1 = qt1;
    }

    public String[] getQt2() {
        return Qt2;
    }

    public void setQt2(String[] qt2) {
        Qt2 = qt2;
    }

    public String[] getQt3() {
        return Qt3;
    }

    public void setQt3(String[] qt3) {
        Qt3 = qt3;
    }

    public String[] getQt4() {
        return Qt4;
    }

    public void setQt4(String[] qt4) {
        Qt4 = qt4;
    }

    public String[] getQt5() {
        return Qt5;
    }

    public void setQt5(String[] qt5) {
        Qt5 = qt5;
    }

    public String[] getQt6() {
        return Qt6;
    }

    public void setQt6(String[] qt6) {
        Qt6 = qt6;
    }

    public String[] getQt7() {
        return Qt7;
    }

    public void setQt7(String[] qt7) {
        Qt7 = qt7;
    }

    public String[] getQt8() {
        return Qt8;
    }

    public void setQt8(String[] qt8) {
        Qt8 = qt8;
    }

    public String[] getQt9() {
        return Qt9;
    }

    public void setQt9(String[] qt9) {
        Qt9 = qt9;
    }

    //    ArrayList<StoryQuestions> storyQuestionsList = new ArrayList<>();

//    public StoryDataParser(int storyId, ArrayList<StoryQuestions> storyQuestionsList) {
//        this.storyId = storyId;
//        this.storyQuestionList = storyQuestionsList;
//    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

//    public ArrayList<StoryQuestions> getStoryQuestionsList() {
//        return storyQuestionsList;
//    }
//
//    public void setStoryQuestionsList(ArrayList<StoryQuestions> storyQuestionsList) {
//        this.storyQuestionsList = storyQuestionsList;
//    }

}
