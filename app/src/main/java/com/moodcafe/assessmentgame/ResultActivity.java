package com.moodcafe.assessmentgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    Button ok;
    TextView result1to4;
    TextView result5;
    TextView result6;
    TextView result7;
    TextView result8;
    AnalysisDataManager analysisDataManager;
    int storyId;
    RadioGroup radioGroup1to4, radioGroup5, radioGroup6, radioGroup7, radioGroup8;
    JSONObject jsonObject = new JSONObject();
    String URL ;
    SessionManager sessionManager;
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        storyId = intent.getIntExtra("Story Id", 1);
        analysisDataManager = new AnalysisDataManager(this);
        result1to4 = findViewById(R.id.result1to4);
        result5 = findViewById(R.id.result5);
        result6 = findViewById(R.id.result6);
        result7 = findViewById(R.id.result7);
        result8 = findViewById(R.id.result8);
        ok = findViewById(R.id.ok);

        sessionManager = new SessionManager(this);
        hashMap =  sessionManager.getUserDetails();


        radioGroup1to4 = findViewById(R.id.radiogroup1);
        radioGroup5 = findViewById(R.id.radiogroup2);
        radioGroup6 = findViewById(R.id.radiogroup3);
        radioGroup7 = findViewById(R.id.radiogroup4);
        radioGroup8 = findViewById(R.id.radiogroup5);

        try {
            jsonObject.put("storyId", storyId);
            jsonObject.put("result1to4", null);
            jsonObject.put("result5", null);
            jsonObject.put("result6", null);
            jsonObject.put("result7", null);
            jsonObject.put("result8", null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ok button handle
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Message is", jsonObject.toString());
//                updateToServer();
                onBackPressed();
            }
        });

        //showing the result after getting it for AnalysisDataManager
        result1to4.setText(analysisDataManager.getresult1to4(storyId));
        result5.setText(analysisDataManager.getresult5(storyId));
        result6.setText(analysisDataManager.getresult6(storyId));
        result7.setText(analysisDataManager.getresult7(storyId));
        result8.setText(analysisDataManager.getresult8(storyId));
        Log.d("json string", analysisDataManager.getAnalysisJson());
        userSatisfaction();

    }

    private void updateToServer() {
        //getting the current time
        String time = new SimpleDateFormat("yyyy-MMM-d HH:mm:ss").format(Calendar.getInstance().getTime());
        String email = hashMap.get(SessionManager.KEY_EMAIL);

        try {

            jsonObject.put("userId",email);
            jsonObject.put("timeStamp",time);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){//getting headers
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;

            }

        };

        requestQueue.add(jsonObjectRequest);
    }


    public void userSatisfaction() {


        radioGroup1to4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
               //comparing with yes option
                if (R.id.yes1 == id)
                    addBoolean(true, radioGroup1to4.getId());
                else addBoolean(false, radioGroup1to4.getId());
            }
        });

        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (R.id.yes2 == id)
                    addBoolean(true, radioGroup5.getId());
                else addBoolean(false, radioGroup5.getId());
            }
        });

        radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (R.id.yes3 == id)
                    addBoolean(true, radioGroup6.getId());
                else addBoolean(false, radioGroup6.getId());
            }
        });

        radioGroup7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (R.id.yes4 == id)
                    addBoolean(true, radioGroup7.getId());
                else addBoolean(false, radioGroup7.getId());
            }
        });

        radioGroup8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (R.id.yes5 == id)
                    addBoolean(true, radioGroup8.getId());
                else addBoolean(false, radioGroup8.getId());
                Intent intent = new Intent(getApplicationContext(),FeedbackActivity.class);
                startActivity(intent);
            }
        });


    }

    //to add boolean value agains each result
    public void addBoolean(boolean bool, int id) {
        try {
            if (id == R.id.radiogroup1)
                jsonObject.put("result1to4", bool);
            else if (id == R.id.radiogroup2)
                jsonObject.put("result5", bool);
            else if (id == R.id.radiogroup3)
                jsonObject.put("result6", bool);
            else if (id == R.id.radiogroup4)
                jsonObject.put("result7", bool);
            else jsonObject.put("result8", bool);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
