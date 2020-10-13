package com.example.tweeter.model.Response;

public class UserStatsResponse {
    public UserStatsResponse(int numFollowers, int numPeopleTheyAreFollowing) {
        this.numFollowers = numFollowers;
        this.numPeopleFollowing = numPeopleTheyAreFollowing;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumPeopleTheyAreFollowing() {
        return numPeopleFollowing;
    }

    private int numFollowers;
    private int numPeopleFollowing;


}
