package com.example.tweeter.model.request;

import com.example.tweeter.model.domain.User;

import java.io.Serializable;

public class FollowingRequest implements Serializable {
    public FollowingRequest(User personWhoFollows, int limit, User previousLast) {
        this.lastOneGotten = previousLast;
        this.personWhoFollows = personWhoFollows;
        this.limit = limit;
    }

    public User getPersonWhoFollows() {
        return personWhoFollows;
    }

    public int getLimit() {
        return limit;
    }

    private User personWhoFollows;

    public User getLastOneGotten() {
        return lastOneGotten;
    }

    private User lastOneGotten;
    private int limit;
}
