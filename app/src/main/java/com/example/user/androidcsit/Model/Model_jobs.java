package com.example.user.androidcsit.Model;

/**
 * Created by User on 9/22/2018.
 */

public class Model_jobs {
    private String jobs_title;
    private String location;
    private String position;
    private String deadline;
    private String image;
    private String company;
    private boolean isFavourite;

    public Model_jobs(String jobs_title, String location, String position, String deadline, String company, boolean isFavourite) {
        this.jobs_title = jobs_title;
        this.location = location;
        this.position = position;
        this.deadline = deadline;
        this.company = company;
        this.isFavourite = isFavourite;
    }

    public Model_jobs(String jobs_title, String location, String position, String deadline, String image, String company, boolean isFavourite) {
        this.jobs_title = jobs_title;
        this.location = location;
        this.position = position;
        this.deadline = deadline;
        this.image = image;
        this.company = company;
        this.isFavourite = isFavourite;
    }

    public String getJobs_title() {
        return jobs_title;
    }

    public void setJobs_title(String jobs_title) {
        this.jobs_title = jobs_title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
