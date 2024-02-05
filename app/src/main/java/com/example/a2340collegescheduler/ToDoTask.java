package com.example.a2340collegescheduler;

public class ToDoTask {
    public String id; // Unique identifier
    public String taskDescription;
    public boolean isCompleted;

    public ToDoTask(String id, String taskDescription, boolean isCompleted) {
        this.id = id;
        this.taskDescription = taskDescription;
        this.isCompleted = isCompleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

