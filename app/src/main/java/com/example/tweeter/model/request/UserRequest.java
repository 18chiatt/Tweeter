package com.example.tweeter.model.request;

public class UserRequest {
    public UserRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    private String alias;
}
