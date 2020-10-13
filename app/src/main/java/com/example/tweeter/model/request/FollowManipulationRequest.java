package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

public class FollowManipulationRequest {
    public FollowManipulationRequest(User personWhoFollows, User personWhoIsFollowed, boolean addFollow) {
        this.personWhoFollows = personWhoFollows;
        this.personWhoIsFollowed = personWhoIsFollowed;
        this.addFollow = addFollow;
    }

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
}
