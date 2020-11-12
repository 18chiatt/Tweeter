package com.example.tweeter.model.Response;

public class UserStatsResponse {
    public UserStatsResponse(int numFollowers, int numPeopleTheyAreFollowing) {
        this.numFollowers = numFollowers;
        this.numPeopleTheyAreFollowing = numPeopleTheyAreFollowing;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumPeopleTheyAreFollowing() {
        return numPeopleTheyAreFollowing;
    }

    private int numFollowers;
    private int numPeopleTheyAreFollowing;


}
