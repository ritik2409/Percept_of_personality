package com.moodcafe.assessmentgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;

/**
 * Created by ritik on 5/16/2018.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "myPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //firstname
    public static final String KEY_FIRSTNAME = "firstname";

    //lastname
    public static final String KEY_LASTNAME = "lastname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    public static final String KEY_FBLOG = "fblog";

    public static final String KEY_GOOGLELOG = "googlelog";

    public static final String KEY_EMAILLOG = "emaillog";

    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String firstname, String lastname, String email, String type) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        //Storing firstname in pref
        editor.putString(KEY_FIRSTNAME, firstname);

        //Storing lastname in pref
        editor.putString(KEY_LASTNAME, lastname);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        editor.putBoolean(KEY_FBLOG, false);
        editor.putBoolean(KEY_GOOGLELOG, false);
        editor.putBoolean(KEY_EMAILLOG, false);
        editor.putBoolean(type, true);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            // Starting Login Activity
            context.startActivity(i);


        }

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        //user firstname
        user.put(KEY_FIRSTNAME, pref.getString(KEY_FIRSTNAME, null));

        //user lastname
        user.put(KEY_LASTNAME, pref.getString(KEY_LASTNAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {


        if (pref.getBoolean(KEY_GOOGLELOG, false) == true)

        {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    // Clearing all data from Shared Preferences
                    editor.clear();
                    editor.commit();
                    gotoLogin();
                }
            });
        } else if (pref.getBoolean(KEY_FBLOG, false) == true) {
            LoginManager.getInstance().logOut();
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();
            gotoLogin();
        } else {// Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();
        }


    }


    private void gotoLogin() {
        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);

    }
    /*
     * Quick check for login
     * */
    // Get Login State


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
