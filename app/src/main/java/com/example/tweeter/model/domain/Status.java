package com.example.tweeter.model.domain;

import com.google.gson.annotations.Expose;

import java.time.Instant;

public class Status implements Comparable<Status> {
    public Status(String message, Long timeOfPost, User saidBy) {
        this.message = message;
        this.timeOfPost = timeOfPost;
        this.saidBy = new User(saidBy.getFirstName(),saidBy.getLastName(),saidBy.getUserName(),saidBy.getImageURL());
    }

    public String getMessage() {
        return message;
    }

    public Long getTimeOfPost() {
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
    @Expose(serialize = true, deserialize = true)
    private String message;
    @Expose(serialize = false, deserialize = true)
    private Long timeOfPost;

    public User getSaidBy() {
        return saidBy;
    }
    @Expose
    private User saidBy;


    @Override
    public int compareTo(Status o) {
        return o.timeOfPost.compareTo(timeOfPost);
    }
}
