package com.example.tweeter.model.request;

import android.graphics.Bitmap;

import java.io.Serializable;

public class RegisterRequest implements Serializable {
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getImage() {
        return image;
    }

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String image;

    public RegisterRequest(String username, String password, String firstName, String lastName,String image) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }



}
