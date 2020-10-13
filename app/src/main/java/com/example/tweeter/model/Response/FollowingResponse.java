package com.example.tweeter.model.Response;

import com.example.tweeter.model.domain.User;

import java.util.List;

public class FollowingResponse {
    public FollowingResponse(List<User> usersTheyAreFollowing, boolean hasMore) {
        this.usersTheyAreFollowing = usersTheyAreFollowing;
        this.hasMore = hasMore;
    }

    public List<User> getUsersTheyAreFollowing() {
        return usersTheyAreFollowing;
    }

    public boolean hasMore() {
        return hasMore;
    }

    List<User> usersTheyAreFollowing;
    boolean hasMore;
}
