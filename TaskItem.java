package com.example.reminderapplicatiom;

public class TaskItem {
    private String taskName;
    private boolean isDone;
    private int priority; // 0 = Low, 1 = Medium, 2 = High

    public TaskItem(String taskName, boolean isDone, int priority) {
        this.taskName = taskName;
        this.isDone = isDone;
        this.priority = priority;
    }

    public String getTaskName() { return taskName; }

    public boolean isDone() { return isDone; }
    public void setDone(boolean done) { isDone = done; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
}