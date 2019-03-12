package com.example.user.androidcsit.Model;

/**
 * Created by User on 8/30/2018.
 */

public class Model_notice {
    private String title;
    private String datte;
    private String description;

    public Model_notice(String title, String datte, String description) {
        this.title = title;
        this.datte = datte;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatte() {
        return datte;
    }

    public void setDatte(String datte) {
        this.datte = datte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
