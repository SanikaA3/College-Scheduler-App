package com.example.a2340collegescheduler;

public class Exam {
    public String id;
    public String date;
    public String time;
    public String location;

    public Exam(String id, String date, String time, String location) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
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
