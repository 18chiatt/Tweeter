package com.example.tweeter.model.domain;

import java.time.Instant;

public class Status implements Comparable<Status> {
    public Status(String message, Instant timeOfPost, User saidBy) {
        this.message = message;
        this.timeOfPost = timeOfPost;
        this.saidBy = saidBy;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimeOfPost() {
        return timeOfPost;
    }


    @Override
    public String toString() {
        return "Status{" +
                "message='" + message + '\'' +
                ", timeOfPost=" + timeOfPost.toString() +
                ", saidBy=" + saidBy +
                '}';
    }

    private String message;
    private Instant timeOfPost;

    public User getSaidBy() {
        return saidBy;
    }

    private User saidBy;


    @Override
    public int compareTo(Status o) {
        return o.timeOfPost.compareTo(timeOfPost);
    }
}
