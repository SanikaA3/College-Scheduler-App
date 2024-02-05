package com.example.a2340collegescheduler;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignments")
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id; // Unique identifier, auto-generated

    public String title; // Assignment title
    public String dueDate; // Due date in "YYYY-MM-DD" format
    public String dueTime; // Due time, assumed to be in "HH:MM" format
    public String amPm; // "AM" or "PM"
    public String associatedClass; // The class this assignment is associated with

    // Constructor without id, as it will be auto-generated
    public Assignment(String title, String dueDate, String dueTime, String amPm, String associatedClass) {
        this.title = title;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.amPm = amPm;
        this.associatedClass = associatedClass;
    }

    public String getTitle() {
        return title;
    }

    public String getAssociatedClass() {
        return associatedClass;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getAmPm() {
        return amPm;
    }


    // Assume getters and setters are here...
}
