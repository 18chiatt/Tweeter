package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

import java.io.Serializable;

public class UserStatsRequest implements Serializable {
    public UserStatsRequest(User toFindOf) {
        this.toFindOf = toFindOf;
    }

    public User getToFindOf() {
        return toFindOf;
    }

    private User toFindOf;

}
