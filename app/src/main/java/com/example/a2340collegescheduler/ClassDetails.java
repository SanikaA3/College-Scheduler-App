package com.example.a2340collegescheduler;

public class ClassDetails {
    private String courseName;
    private String time;
    private String instructor;
    private String timePeriod;
    private boolean[] days;

    public ClassDetails(String courseName, String time, String instructor, String timePeriod, boolean[] days){
        this.courseName = courseName;
        this.time = time;
        this.instructor = instructor;
        this.timePeriod = timePeriod;
        this.days = days;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }
}
