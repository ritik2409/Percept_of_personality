package com.moodcafe.assessmentgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
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
    private static final String PREF_NAME = "session";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    //uniqueId
    public static final String KEY_UNIQUEID = "UniqueID";


    //firstname
    public static final String KEY_FIRSTNAME = "firstname";

    //lastname
    public static final String KEY_LASTNAME = "lastname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // fb login type
    public static final String KEY_FBLOG = "fblog";

    // google login type
    public static final String KEY_GOOGLELOG = "googlelog";

    // email login type
    public static final String KEY_EMAILLOG = "emaillog";

    //skip login type
    public static final String KEY_SKIPLOG = "skiplog";

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

    //handle login skip part
    public void skipLogin() {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        editor.putString(KEY_UNIQUEID, android_id);
        editor.putBoolean(KEY_SKIPLOG, true);
        editor.commit();

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
     * logout
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
                    editor.putBoolean(IS_LOGIN, false);
                }
            });
        } else if (pref.getBoolean(KEY_FBLOG, false) == true) {
            LoginManager.getInstance().logOut();
            editor.putBoolean(IS_LOGIN, false);
        } else if (pref.getBoolean(KEY_EMAILLOG, false) == true)
            editor.putBoolean(IS_LOGIN, false);
        else editor.putBoolean(KEY_SKIPLOG, false);
        gotoLogin();


    }


    public void gotoLogin() {

        // After logout redirect user to Loing Activity
      context.getSharedPreferences(PREF_NAME,0).edit().clear().commit();
        Intent i = new Intent(context, LoginActivity.class);

        // Staring Login Activity
        context.startActivity(i);
        ((Activity) context).finish();


    }

    /*
     * Quick check for login
     * */
    // Get Login State

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isLoginSkipped() {
        return pref.getBoolean(KEY_SKIPLOG, false);
    }
}
