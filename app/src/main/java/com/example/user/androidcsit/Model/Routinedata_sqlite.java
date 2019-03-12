package com.example.user.androidcsit.Model;

/**
 * Created by Razu on 12/3/2017.
 */

public class Routinedata_sqlite {
    private int id;
    private String day;
    private String start_times;
    private String end_times;
    private String subject;
    private String teacher;

    public Routinedata_sqlite() {
    }

    public Routinedata_sqlite(int id) {
        this.id = id;
    }

    public Routinedata_sqlite(int id, String day, String start_times, String end_times, String subject, String teacher) {
        this.id = id;
        this.day = day;
        this.start_times = start_times;
        this.end_times = end_times;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Routinedata_sqlite(String day, String start_times, String end_times, String subject, String teacher) {
        this.day = day;
        this.start_times = start_times;
        this.end_times = end_times;
        this.subject = subject;
        this.teacher = teacher;
    }

    public Routinedata_sqlite(String start_times, String end_times, String subject, String teacher) {
        this.start_times = start_times;
        this.end_times = end_times;
        this.subject = subject;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart_times() {
        return start_times;
    }

    public void setStart_times(String start_times) {
        this.start_times = start_times;
    }

    public String getEnd_times() {
        return end_times;
    }

    public void setEnd_times(String end_times) {
        this.end_times = end_times;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}

