package com.example.user.androidcsit.Model;

/**
 * Created by User on 9/18/2018.
 */

public class Model_questions {
    private String link;
    private String name;

    public Model_questions(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
