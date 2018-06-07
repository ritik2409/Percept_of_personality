package com.example.android.assessmentgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class signup extends AppCompatActivity {

    EditText fname, lname, emailId, passw;
    //    Button signup;
    Register register;
    String key = "AELODVF8KDCMOK7RLZU8K6P0RI8ZT4B4";
    String URL;
    CheckBox checkBox;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        emailId = (EditText) findViewById(R.id.email);
        passw = (EditText) findViewById(R.id.pass);
        URL = "http://159.89.163.196/api/game/user/register";
        checkBox = (CheckBox) findViewById(R.id.show_hide_password);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean status = checkBox.isChecked();

                if (!status)
                    passw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                else
                    passw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });
        jsonObject = new JSONObject();


    }


    public void register(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(signup.this);


        try {
            jsonObject.put("key", key);
            jsonObject.put("fname", fname.getText().toString());
            jsonObject.put("lname", lname.getText().toString());
            jsonObject.put("email", emailId.getText().toString());
            jsonObject.put("passw", passw.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("message is:", response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("dikkat hai...", error.getMessage());

                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };

//        StringRequest request = new StringRequest(Request.Method.POST,
//                URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Response", response);
//
//                        try {
//                            jsonObject = new JSONObject(response);
//                            Log.d("Message is:",jsonObject.getString("message"));
////                            if (jsonObject.getInt("status") == 1) {
////                                Intent intent = new Intent(signup.this, GameDetails.class);
////                                startActivity(intent);
////                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.toString());
//                        Log.e("bohot dikkat hai","zindagi m");
//
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("key", key);
//                params.put("fname", fname.getText().toString());
//                params.put("lname", lname.getText().toString());
//                params.put("email", emailId.getText().toString());
//                params.put("passwd", passw.getText().toString());
//                return params;
//            }
//
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> headers = new HashMap<String, String>();
////                params.put("Authorization","Basic Og==");
//                headers.put("Content-Type","application/json; charset=utf-8");
//                return headers;
//
//            }
//
//        };
        requestQueue.add(request);
    }
}
