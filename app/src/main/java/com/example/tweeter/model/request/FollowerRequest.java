package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

public class FollowerRequest {
    public FollowerRequest(User whoTheyFollow, int maxToGet, User lastOneGotten) {
        this.whoTheyFollow = whoTheyFollow;
        this.maxToGet = maxToGet;
        this.previousLast = lastOneGotten;
    }

    public User getWhoTheyFollow() {
        return whoTheyFollow;
    }

    public int getMaxToGet() {
        return maxToGet;
    }

    private User whoTheyFollow;
    private int maxToGet;

    public User getPreviousLast() {
        return previousLast;
    }

    private User previousLast;
}
