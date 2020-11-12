package com.example.tweeter.model.request;

import java.io.Serializable;

public class UserRequest implements Serializable {
    public UserRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    private String alias;
}
