package com.moodcafe.assessmentgame;


import java.util.ArrayList;

/**
 * Created by ritik on 6/14/2018.
 */

public class GameResumeParser {

    ArrayList<GameProgress> values = new ArrayList<>();

    public GameResumeParser(ArrayList<GameProgress> values) {
        this.values = values;
    }

    public ArrayList<GameProgress> getValues() {
        return values;
    }

    public void setValues(ArrayList<GameProgress> values) {
        this.values = values;
    }

}
