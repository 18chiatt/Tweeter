package com.example.tweeter.model.Response;



public class FollowManipulationDTO {


    public boolean isNowFollowing() {
        return isNowFollowing;
    }


    public FollowManipulationDTO( boolean isNowFollowing,boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
        this.isNowFollowing = isNowFollowing;
    }

    public boolean isWasSuccess() {
        return wasSuccess;
    }

    private boolean wasSuccess;
    private boolean isNowFollowing;

}
