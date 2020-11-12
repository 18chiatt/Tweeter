package com.example.tweeter.model.domain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable {
    public User(String firstName, String lastName, String userName, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.imageURL = imageURL;
    }



    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
    @Expose(serialize = true, deserialize = true)
    private String firstName;
    @Expose(serialize = true, deserialize = true)
    private String lastName;
    @Expose(serialize = true, deserialize = true)
    private String userName;
    @Expose(serialize = true, deserialize = true)
    private String imageURL;
    @Expose(serialize = false, deserialize = false)
    private  byte [] imageBytes;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
    public byte [] getImageBytes() {
        return imageBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(imageURL, user.imageURL);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, lastName, userName, imageURL);
        result = 31 * result + Arrays.hashCode(imageBytes);
        return result;
    }
}
