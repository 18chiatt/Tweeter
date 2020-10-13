package com.example.tweeter.model.Response;

import com.example.tweeter.model.domain.User;

public class UserResponse {


    public User getToRespondWith() {
        return toRespondWith;
    }

    public UserResponse(User toRespondWith, boolean success) {
        this.toRespondWith = toRespondWith;
        this.success = success;
    }

    private User toRespondWith;

    public boolean isSuccess() {
        return success;
    }

    private boolean success;
}
