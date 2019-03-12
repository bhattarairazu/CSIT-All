package com.example.user.androidcsit;




import com.example.user.androidcsit.Model.Postid;

import java.util.Date;

public class Model_ddatta extends Postid {

    private String Postdetail, userid, username,profileimage;
    public Date timestamp;

    public Model_ddatta(String postdetail, String userid, String username,String profileimage, Date timestamp) {
        Postdetail = postdetail;
        this.userid = userid;
        this.username = username;
        this.profileimage = profileimage;
        this.timestamp = timestamp;
    }

    public Model_ddatta() {
    }

    public String getPostdetail() {
        return Postdetail;
    }

    public void setPostdetail(String postdetail) {
        Postdetail = postdetail;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }
}

