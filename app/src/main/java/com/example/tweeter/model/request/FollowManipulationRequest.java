package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

import java.io.Serializable;

public class FollowManipulationRequest implements Serializable {


    public User getPersonWhoFollows() {
        return personWhoFollows;
    }

    public User getPersonWhoIsFollowed() {
        return personWhoIsFollowed;
    }

    public boolean isAddFollow() {
        return addFollow;
    }

    private User personWhoFollows;
    private User personWhoIsFollowed;
    private boolean addFollow;

    public FollowManipulationRequest(User personWhoFollows, User personWhoIsFollowed, boolean addFollow, String authToken) {
        this.personWhoFollows = personWhoFollows;
        this.personWhoIsFollowed = personWhoIsFollowed;
        this.addFollow = addFollow;
        this.authToken = authToken;
    }

    private String authToken;
}
