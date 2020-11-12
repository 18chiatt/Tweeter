package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.Status;

import java.io.Serializable;

public class PostStatusRequest implements Serializable {
    public Status getTheStatus() {
        return theStatus;
    }


    public PostStatusRequest(Status theStatus, String authToken) {
        this.theStatus = theStatus;
        this.authToken = authToken;
    }

    private Status theStatus;
    private String authToken;
}
