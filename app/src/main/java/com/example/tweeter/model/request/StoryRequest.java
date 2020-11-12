package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;

import java.io.Serializable;

public class StoryRequest implements Serializable {
    public int getMaxToGet() {
        return maxToGet;
    }

    public User getToGetOf() {
        return toGetOf;
    }

    public Status getPreviousLast() {
        return previousLast;
    }

    int maxToGet;
    User toGetOf;
    Status previousLast;

    public StoryRequest(int maxToGet, User toGetOf, Status previousLast) {
        this.maxToGet = maxToGet;
        this.toGetOf = toGetOf;
        this.previousLast = previousLast;
    }
}
