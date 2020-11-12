package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;

import java.io.Serializable;

public class FeedRequest implements Serializable {
    public FeedRequest(User toGetFeedOf, int maxToGet, Status previousLast) {
        this.toGetFeedOf = toGetFeedOf;
        this.maxToGet = maxToGet;
        this.previousLast = previousLast;
    }

    public User getToGetFeedOf() {
        return toGetFeedOf;
    }

    public int getMaxToGet() {
        return maxToGet;
    }

    public Status getPreviousLast() {
        return previousLast;
    }

    private User toGetFeedOf;
    private int maxToGet;
    private Status previousLast;
}
