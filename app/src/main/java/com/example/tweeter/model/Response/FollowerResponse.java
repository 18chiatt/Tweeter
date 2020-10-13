package com.example.tweeter.model.Response;

import com.example.tweeter.model.domain.User;

import java.util.List;

public class FollowerResponse {
    public FollowerResponse(List<User> followers, boolean hasMore) {
        this.followers = followers;
        this.hasMore = hasMore;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public boolean hasMore() {
        return hasMore;
    }

    List<User> followers;
    boolean hasMore;
}
