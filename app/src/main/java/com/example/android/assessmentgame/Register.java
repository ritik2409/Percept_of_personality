package com.example.android.assessmentgame;

/**
 * Created by ritik on 6/5/2018.
 */

public class Register {

    private String key;
    private String fname;
    private String lname;
    private String email;
    private String passw;

    public Register(String key, String fname, String lname, String email, String passw) {
        this.key = key;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.passw = passw;
    }


    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
