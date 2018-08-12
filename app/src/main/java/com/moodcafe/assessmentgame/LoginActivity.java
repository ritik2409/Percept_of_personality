package com.moodcafe.assessmentgame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailtext, passwtext;
    String email, fname, lname, name;
    Button login;
    String key = "AELODVF8KDCMOK7RLZU8K6P0RI8ZT4B4";
    String URL;
    LoginButton loginButton;
    SignInButton button;
    GoogleApiClient googleApiClient;
    CallbackManager callbackManager;
    SessionManager sessionManager;
    JSONObject jsonObject;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        emailtext = (EditText) findViewById(R.id.emailtext);
        passwtext = (EditText) findViewById(R.id.passText);
        login = (Button) findViewById(R.id.letsgo);
        URL = "http://139.59.64.113/api/game/user/login";
        sessionManager = new SessionManager(this);
        progressDialog = new ProgressDialog(this);
        jsonObject = new JSONObject();
        button = findViewById(R.id.google_signin);
        setupGoogleLogin();

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        loginButton = findViewById(R.id.fb_login_btn);
        setupFBLogin();

        //action on clicking login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsOnline.connectedToInternet(getApplicationContext())) {
                    showDialog();
                }

                progressDialog.setMessage("Logging...");
                //checking the empty fields
                if (checkEmptyField()) {
                    progressDialog.show();
                    try {
                        jsonObject.put("key", key);
                        jsonObject.put("email", emailtext.getText().toString());
                        jsonObject.put("passwd", passwtext.getText().toString());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //json request to the server for login
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        //handling the response from the server
                                        Log.d("message is:", response.getString("message"));
                                        progressDialog.cancel();
                                        JSONObject object = response.getJSONObject("userdata");
                                        fname = object.getString("first_name");
                                        lname = object.getString("last_name");
                                        email = object.getString("email");
                                        completeLogin(SessionManager.KEY_EMAILLOG);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //handling if any error in making the request
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }

                    ) {//getting the headers : content_type : json
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

    //google login
    private void setupGoogleLogin() {

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!IsOnline.connectedToInternet(getApplicationContext())) {
                    showDialog();
                } else {
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent, 9001);
                }
            }
        });

    }

    //fb login
    private void setupFBLogin() {
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    email = object.getString("email");
                                    name = object.getString("name");
                                    completeLogin(SessionManager.KEY_FBLOG);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                if (!IsOnline.connectedToInternet(getApplicationContext())) {
                    showDialog();
                }
                Toast.makeText(LoginActivity.this, "Error logging!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //to create login session with first, lname, email and the type of login(normal, google or fb) i.e. storing these fields in the app
    //so for the next time it can skip the login screen
    public void completeLogin(String type) {
        seperatename();
        sessionManager.createLoginSession(fname, lname, email, type);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

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

    //further steps in google and fb login such as to access the name and email
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 9001) {

            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (signInResult.isSuccess()) {
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
                name = signInAccount.getDisplayName();
                email = signInAccount.getEmail();
                completeLogin(SessionManager.KEY_GOOGLELOG);
            }

        } else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //to seperate the first and the last name from google and fb login
    private void seperatename() {
        int spacenum = name.indexOf(" ");
        fname = name.substring(0, spacenum);
        lname = name.substring(spacenum).trim();


    }

    //to check whether the string entered in the email field is in the format of email address
    public boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //to check whether the length of the password is atleast of 8 characters
    public boolean isPassword(String password) {
        return (!TextUtils.isEmpty(password) && password.length() >= 8);
    }

    //to check whether the email and password fields are empty
    private boolean checkEmptyField() {
        if (!isEmail(emailtext)) {
            emailtext.setError("Enter valid email.");
            return false;
        }
        if (!isPassword(passwtext.getText().toString())) {
            passwtext.setError("Enter valid password.");
            return false;
        }
        return true;

    }

    //to start SignupActivity activity when user clicks on SignupActivity hyperlink
    public void tosignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    //function called when user skips the login screen
    public void loginSkipped(View view) {

        sessionManager.skipLogin();
        Intent it = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(it);
        finish();

    }
}
