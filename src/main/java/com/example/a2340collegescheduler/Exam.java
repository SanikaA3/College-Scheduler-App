package com.example.a2340collegescheduler;

public class Exam {
    public String courseName;
    public boolean[] date;
    public String time;
    public String location;

    public Exam(String id, boolean[] date, String time, String location) {
        this.courseName = id;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public boolean[] getDate() {
        return date;
    }
    public void setDate(boolean[] date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
