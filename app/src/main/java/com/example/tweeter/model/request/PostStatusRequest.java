package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.Status;

public class PostStatusRequest {
    public Status getTheStatus() {
        return theStatus;
    }

    public PostStatusRequest(Status theStatus) {
        this.theStatus = theStatus;
    }

    private Status theStatus;
}
