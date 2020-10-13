package com.example.tweeter.model.domain;

public class Follow {
    public Follow(User follower, User personBeingFollowed) {
        this.follower = follower;
        this.personBeingFollowed = personBeingFollowed;
    }

    public User getFollower() {
        return follower;
    }

    public User getPersonBeingFollowed() {
        return personBeingFollowed;
    }

    private User follower;
    private User personBeingFollowed;

}
