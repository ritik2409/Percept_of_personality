package com.moodcafe.assessmentgame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    TextInputEditText fname, lname, emailId, passw;
    Button sign_button;
    String key = "AELODVF8KDCMOK7RLZU8K6P0RI8ZT4B4";
    String URL;
    JSONObject jsonObject;
    ProgressDialog progressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = (TextInputEditText) findViewById(R.id.fname);
        lname = (TextInputEditText) findViewById(R.id.lname);
        emailId = (TextInputEditText) findViewById(R.id.email);
        passw = (TextInputEditText) findViewById(R.id.pass);
        sign_button = (Button) findViewById(R.id.sign_button);
        URL = "http://139.59.64.113/api/game/user/register";
        jsonObject = new JSONObject();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        sessionManager = new SessionManager(this);

        progressDialog = new ProgressDialog(this);
        //handling actions after the signup button is clicked
        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsOnline.connectedToInternet(getApplicationContext())) {
                    showDialog();
                }
                progressDialog.setMessage("Registering...");
                if (checkEmptyFields()) {
                    progressDialog.show();
                    try {
                        jsonObject.put("key", key);
                        jsonObject.put("fname", fname.getText().toString());
                        jsonObject.put("lname", lname.getText().toString());
                        jsonObject.put("email", emailId.getText().toString());
                        jsonObject.put("passwd", passw.getText().toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Making the json request to the server for signup
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Log.d("message is:", response.getString("message"));
                                        sessionManager.createLoginSession(fname.getText().toString(), lname.getText().toString(), emailId.getText().toString(), SessionManager.KEY_EMAILLOG);
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        progressDialog.cancel();
                                        Toast.makeText(SignupActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("dikkat hai...", error.getMessage());
                                    progressDialog.cancel();
                                    Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }

                    ) {
                        //getting the headers : content_type : json
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            return headers;

                        }

                    };

                    requestQueue.add(request);
                }


            }

        });


    }

    //dialog box to show no internet connection
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No Interet Connection")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (IsOnline.connectedToInternet(getApplicationContext())) {
                            dialogInterface.dismiss();
                        } else showDialog();
                    }
                })
                .create()
                .show();


    }

    //Matching the email address pattern with the string in the email field
    public boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //checking the length of the password
    public boolean isPassword(String password) {

        return (!TextUtils.isEmpty(password) && password.length() >= 8);
    }

    //check if there is any empty fields
    private boolean checkEmptyFields() {
        if ((TextUtils.isEmpty(fname.getText())) || TextUtils.isEmpty(lname.getText())) {
            fname.setError("This field can be blank.");
            lname.setError("This field can be blank.");
            return false;
        }

        if (!isEmail(emailId)) {
            emailId.setError("Enter valid email");
            return false;
        }

        if (!isPassword(passw.getText().toString())) {
            passw.setError("Enter a password of atleast 8 characters");
            return false;
        }

        return true;


    }


    public void tologin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

