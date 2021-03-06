package com.example.tweeter.model.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
