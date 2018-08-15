package com.moodcafe.assessmentgame;

import java.util.ArrayList;

/**
 * Created by ritik on 7/1/2018.
 */

public class AnalysisParser {

    private ArrayList<StoryDataParser> storyDataParserList = new ArrayList<>();

    public AnalysisParser(ArrayList<StoryDataParser> storyDataParserList) {
        this.storyDataParserList = storyDataParserList;
    }

    public ArrayList<StoryDataParser> getStoryDataParserList() {
        return storyDataParserList;
    }

    public void setStoryDataParserList(ArrayList<StoryDataParser> storyDataParserList) {
        this.storyDataParserList = storyDataParserList;
    }


}
