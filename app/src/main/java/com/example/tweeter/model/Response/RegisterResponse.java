package com.example.tweeter.model.Response;

public class RegisterResponse {
    public RegisterResponse(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public boolean isWasSuccessful() {
        return wasSuccessful;
    }

    private boolean wasSuccessful;
}
