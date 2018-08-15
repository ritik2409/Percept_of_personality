package com.moodcafe.assessmentgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ritik on 7/1/2018.
 */

public class AnalysisDataManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    //GSON Library to convert json to class objects
    Gson gson = new Gson();

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "myPref";
    //analysis started status
    private static final String IS_ANALYSISSTARTED = "is_analysisstarted";
    //result initiated
    private static final String IS_RESULTINITIATED = "is_resultinitiated";


    public AnalysisDataManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public void updateChoice(int storyId, String choiceNum, int questionType) {

        AnalysisParser analysisParser = gson.fromJson(getAnalysisJson(), AnalysisParser.class);
        ArrayList<StoryDataParser> storyDataParsers = analysisParser.getStoryDataParserList();
//        String[] id = new String[5];
        int index;
//        ArrayList<StoryQuestions> storyQuestions;
        for (int i = 0; i < storyDataParsers.size(); i++) {
            if (storyId == storyDataParsers.get(i).getStoryId()) {
                index = idtoindex(String.valueOf(choiceNum.charAt(0)));
                switch (questionType) {

                    case 1:
                        storyDataParsers.get(i).getQt1()[index] = choiceNum;
                        break;
                    case 2:
                        storyDataParsers.get(i).getQt2()[index] = choiceNum;
                        break;
                    case 3:
                        storyDataParsers.get(i).getQt3()[index] = choiceNum;
                        break;
                    case 4:
                        storyDataParsers.get(i).getQt4()[index] = choiceNum;
                        break;
                    case 5:
                        storyDataParsers.get(i).getQt5()[index] = choiceNum;
                        break;
                    case 6:
                        storyDataParsers.get(i).getQt6()[index] = choiceNum;
                        break;
                    case 7:
                        storyDataParsers.get(i).getQt7()[index] = choiceNum;
                        break;
                    case 8:
                        storyDataParsers.get(i).getQt8()[index] = choiceNum;
                        break;
                    case 9:
                        storyDataParsers.get(i).getQt9()[index] = choiceNum;
                }


//                storyQuestions = storyDataParsers.get(i).getStoryQuestionsList();
//                for (int j = 0; j < storyQuestions.size(); j++) {
//                    if (questionType == storyQuestions.get(j).getQuestionType()) {
//                        if (choiceNum == 1)
//                            storyQuestions.get(j).getChoice1().add(sceneId);
//                        else storyQuestions.get(j).getChoice2().add(sceneId);
//                        break;
//                    }
//                }
            }
        }

        String json = gson.toJson(analysisParser);
        editor.putString("Analysis", json);
        editor.commit();
//        Log.d("Json is", json);


    }

    private int idtoindex(String choiceNum) {

        switch (choiceNum) {

            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;


        }

        return 4;


    }

    public void initialiseAnalysis(int storyId) {

        int flag = 0;
        AnalysisParser analysisParser;
        ArrayList<StoryDataParser> data;
//        ArrayList<StoryQuestions> questions = new ArrayList<>();
//        ArrayList<Number> arrayList = new ArrayList<>();
//        for (int i = 1; i <= 8; i++)
//            questions.add(new StoryQuestions(i, arrayList, arrayList));
        if (!isAnalysisStarted()) {
            data = new ArrayList<>();
            data.add(new StoryDataParser(storyId, new String[5], new String[5], new String[5], new String[5], new String[5], new String[5], new String[5], new String[5], new String[5]));
            analysisParser = new AnalysisParser(data);

        } else {
            analysisParser = gson.fromJson(getAnalysisJson(), AnalysisParser.class);
            data = analysisParser.getStoryDataParserList();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getStoryId() == storyId) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                data.add(new StoryDataParser(storyId, new String[5], new String[5], new String[5], new String[5], new String[5], new String[5], new String[5], new String[5], new String[5]));
            }
        }


        String json = gson.toJson(analysisParser);
        editor.putString("Analysis", json);
        editor.putBoolean(IS_ANALYSISSTARTED, true);
        editor.commit();
//        Log.d("Json is", json);

    }

//    public boolean isFeedbackTake(int storyId){
//
//
//    }

    public boolean isResultInitialted() {
        return pref.getBoolean(IS_RESULTINITIATED, false);
    }

    public boolean isAnalysisStarted() {
        return pref.getBoolean(IS_ANALYSISSTARTED, false);
    }

    public String getAnalysisJson() {
        return pref.getString("Analysis", "Not Found");
    }

    public String getresultJson() {
        return pref.getString("ResultActivity Json", "Not Found");
    }

    public void finalResult(int storyId) {

        AnalysisParser analysisParser;
        ArrayList<StoryDataParser> storyDataParsers;
//        ArrayList<StoryQuestions> storyQuestions;
        analysisParser = gson.fromJson(getAnalysisJson(), AnalysisParser.class);
        storyDataParsers = analysisParser.getStoryDataParserList();
//        int[] qts = new int[8];
        int count;
        String output = null;
        String result5 = null, result6 = null, result7 = null, result8 = null;
        for (int i = 0; i < storyDataParsers.size(); i++) {

            if (storyId == storyDataParsers.get(i).getStoryId()) {
//                storyQuestions = storyDataParsers.get(i).getStoryQuestionsList();
                for (int j = 0; j < 8; j++) {
                    switch (j + 1) {

                        case 1:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt1()) == 1)
                                output = "I";
                            else output = "E";
                            break;

                        case 2:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt2()) == 1)
                                output = output + "T";
                            else
                                output = output + "F";
                            break;

                        case 3:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt3()) == 1)
                                output = output + "N";
                            else output = output + "S";
                            break;

                        case 4:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt4()) == 1)
                                output = output + "J";
                            else output = output + "P";
                            break;
                        case 5:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt5()) == 1)
                                result5 = "Anger_high";
                            else
                                result5 = "Anger_low";
                        case 6:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt6()) == 1)
                                result6 = "SelfEsteem_high";
                            else
                                result6 = "SelfEsteem_low";
                        case 7:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt7()) == 1)
                                result7 = "Failure_positive";
                            else result7 = "Failure_negative";
                        case 8:
//                            if (storyQuestions.get(j).getChoice1().size() >= 3)
                            if (orientedtowards(storyDataParsers.get(i).getQt8()) == 1)
                                result8 = "Approval_high";
                            else result8 = "Approval_low";


                    }


                }
                break;

            }

        }

        JSONObject object = new JSONObject();
        try {
            object.put("Story Id", storyId);
            object.put("Result1to4", output);
            object.put("Result5", result5);
            object.put("Result6", result6);
            object.put("Result7", result7);
            object.put("Result8", result8);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONArray jsonArray = null;
        if (!isResultInitialted()) {
            jsonArray = new JSONArray();
        } else try {
            JSONObject jsonObject = new JSONObject(getresultJson());
            jsonArray = jsonObject.getJSONArray("Analysis");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(object);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Analysis", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.putString("ResultActivity Json", jsonObject.toString());
        editor.putBoolean(IS_RESULTINITIATED, true);
        editor.commit();


    }

    private int orientedtowards(String[] qts) {

        String qt;
        int choice;
        int counter1 = 0, counter2 = 0;
        for (int k = 0; k < 5; k++) {
            qt = qts[k];
            choice = Integer.valueOf(qt.charAt(1));
            if (choice == 1)
                counter1++;
            else
                counter2++;

        }
        if (counter1 > counter2)
            return 1;
        return 2;

    }


    public String getContent(String resultType, int storyId) {
        String content = null;
        String contentCode = null;

        try {
            JSONObject jsonObject = new JSONObject(getresultJson());
            JSONArray jsonArray = jsonObject.getJSONArray("Analysis");

            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getInt("Story Id") == storyId)

                    contentCode = jsonArray.getJSONObject(i).getString(resultType);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(loadJSONFromAsset());
            content = jsonObject.getString(contentCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return content;

    }

    public String getresult1to4(int storyId) {

        return getContent("Result1to4", storyId);
    }


    public String getresult5(int storyId) {

        return getContent("Result5", storyId);
    }

    public String getresult6(int storyId) {

        return getContent("Result6", storyId);
    }

    public String getresult7(int storyId) {

        return getContent("Result7", storyId);
    }

    public String getresult8(int storyId) {

        return getContent("Result8", storyId);

    }

    public String loadJSONFromAsset() {

        InputStream is = context.getResources().openRawResource(R.raw.analysis);
        Scanner scanner = new Scanner(is);
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());

        }

        Log.d("json string", builder.toString());

        return builder.toString();


    }


}
