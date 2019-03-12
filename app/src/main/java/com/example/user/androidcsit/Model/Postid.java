package com.example.user.androidcsit.Model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

/**
 * Created by User on 9/15/2018.
 */

public class Postid {
    @Exclude
    public String Postid;
    public <T extends Postid> T withId(@NonNull final String id) {
        this.Postid = id;
        return (T) this;
    }

}
