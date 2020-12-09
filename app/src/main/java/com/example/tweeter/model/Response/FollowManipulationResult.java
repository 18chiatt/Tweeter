package com.example.tweeter.model.Response;

import com.example.tweeter.Server.ModelObserver;

import java.util.List;

public class FollowManipulationResult {


    public boolean isNowFollowing() {
        return nowFollowing;
    }

    public FollowManipulationResult(boolean isNowFollowing, List<ModelObserver> toUpdate) {
        this.nowFollowing = isNowFollowing;
        this.toUpdate = toUpdate;
    }

    public List<ModelObserver> getToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(List<ModelObserver> toUpdate) {
        this.toUpdate = toUpdate;
    }

    public FollowManipulationResult(boolean isNowFollowing) {
        this.nowFollowing = isNowFollowing;
    }


    public boolean isWasSuccess() {
        return wasSuccess;
    }

    private boolean wasSuccess;
    private boolean nowFollowing;
    private List<ModelObserver> toUpdate;
}
