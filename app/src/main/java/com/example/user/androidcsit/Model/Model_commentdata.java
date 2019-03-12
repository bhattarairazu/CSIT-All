package com.example.user.androidcsit.Model;

import java.util.Date;

/**
 * Created by User on 8/28/2018.
 */

public class Model_commentdata {
    private String username,userid,comments,profileimage;
    private Date timestamp;

    public Model_commentdata() {
    }

    public Model_commentdata(String username, String userid, String comments, String profileimage, Date timestamp) {
        this.username = username;
        this.userid = userid;
        this.comments = comments;
        this.profileimage = profileimage;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
