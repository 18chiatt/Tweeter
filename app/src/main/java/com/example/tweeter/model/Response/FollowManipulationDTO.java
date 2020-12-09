package com.example.tweeter.model.Response;



public class FollowManipulationDTO {


    public boolean isNowFollowing() {
        return nowFollowing;
    }


    public FollowManipulationDTO( boolean isNowFollowing,boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
        this.nowFollowing = isNowFollowing;
    }

    public boolean isWasSuccess() {
        return wasSuccess;
    }

    private boolean wasSuccess;
    private boolean nowFollowing;

}
