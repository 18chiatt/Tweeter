package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

import java.io.Serializable;

public class UserStatsRequest implements Serializable {



    public User getToFindOf() {
        return toFindOf;
    }

    public String getAuthToken() {
        return authToken;
    }

    public UserStatsRequest(User toFindOf, String authToken, User whoAsked) {
        this.toFindOf = toFindOf;
        this.authToken = authToken;
        this.whoAsked = whoAsked;
    }

    private User toFindOf;
    private String authToken;
    private User whoAsked;

}
