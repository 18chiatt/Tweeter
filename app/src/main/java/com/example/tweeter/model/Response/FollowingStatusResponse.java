package com.example.tweeter.model.Response;

public class FollowingStatusResponse {
    public FollowingStatusResponse(boolean follows) {
        this.follows = follows;
    }

    public boolean isFollows() {
        return follows;
    }

    private boolean follows;
}
