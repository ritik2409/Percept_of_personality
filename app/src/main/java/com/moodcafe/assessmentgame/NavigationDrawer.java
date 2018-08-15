package com.moodcafe.assessmentgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.HashMap;

public class NavigationDrawer extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private android.support.v7.widget.Toolbar toolbar;
    private FrameLayout frameLayout;
    private SessionManager sessionManager;

    private TextView nametext;
    private String firstname, lastname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        sessionManager = new SessionManager(this);


        frameLayout = (FrameLayout) findViewById(R.id.framelayout);

        HashMap<String, String> user = sessionManager.getUserDetails();
        firstname = user.get(SessionManager.KEY_FIRSTNAME);
        lastname = user.get(SessionManager.KEY_LASTNAME);


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();


        nv = (NavigationView) findViewById(R.id.nv);
        View headerview = nv.getHeaderView(0);
        nametext = headerview.findViewById(R.id.name);
        if (!sessionManager.isLoggedIn()) {
            nametext.setText("Welcome Guest");
        } else {
            nametext.setText(firstname + " " + lastname);
        }

        onPrepare(nv.getMenu());


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.account:
                        if (sessionManager.isLoggedIn()) {
                            frameLayout.removeAllViews();
                            getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new accountFragment()).addToBackStack("account").commit();
                        } else loginredirect();
                        break;

                    case R.id.setting:
                        frameLayout.removeAllViews();
                        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new settingFragment()).addToBackStack("settings").commit();
                        break;
                    case R.id.login:
                        sessionManager.logoutUser();
                        break;
                    case R.id.logout:
                        logoutdialog();
                        break;
                    case R.id.rating:
                        Intent intent1 = new Intent(getApplicationContext(), FeedbackActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.contact:
                        frameLayout.removeAllViews();
                        getSupportFragmentManager().beginTransaction().add(R.id.framelayout, new contactFragment()).addToBackStack("contact").commit();
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);

                return true;
            }
        });


    }

    private void loginredirect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login")
                .setIcon(R.drawable.user)
                .setMessage("You are not logged in. Login first to go through your my account section.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        dialogInterface.cancel();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();

    }

    public void onBackPressed() {


        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            super.onBackPressed();
        }
    }


    private void logoutdialog() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(NavigationDrawer.this);
        alert.setTitle("Logout");
        alert.setMessage("Are you sure?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionManager.logoutUser();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();


    }

    public boolean onPrepare(Menu menu) {
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem login = menu.findItem(R.id.login);
        if (sessionManager.isLoggedIn()) {
            login.setVisible(false);
            logout.setVisible(true);
        }

        if (sessionManager.isLoginSkipped()) {
            login.setVisible(true);
            logout.setVisible(false);
        }


        return true;
    }


    protected void onCreateDrawer(int id) {
        getLayoutInflater().inflate(id, frameLayout);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        t.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        t.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
