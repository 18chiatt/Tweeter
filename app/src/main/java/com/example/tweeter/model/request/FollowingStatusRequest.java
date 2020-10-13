package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

public class FollowingStatusRequest {
    public FollowingStatusRequest(User personWhoFollowsMaybe, User personWhoIsFollowedMaybe) {
        this.personWhoFollowsMaybe = personWhoFollowsMaybe;
        this.personWhoIsFollowedMaybe = personWhoIsFollowedMaybe;
    }

    public User getPersonWhoFollowsMaybe() {
        return personWhoFollowsMaybe;
    }

    public User getPersonWhoIsFollowedMaybe() {
        return personWhoIsFollowedMaybe;
    }

    private User personWhoFollowsMaybe;
    private User personWhoIsFollowedMaybe;

}
