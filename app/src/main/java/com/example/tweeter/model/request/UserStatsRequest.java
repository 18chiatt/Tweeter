package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

public class UserStatsRequest {
    public UserStatsRequest(User toFindOf) {
        this.toFindOf = toFindOf;
    }

    public User getToFindOf() {
        return toFindOf;
    }

    private User toFindOf;

}
