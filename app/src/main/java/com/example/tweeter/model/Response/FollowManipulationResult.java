package com.example.tweeter.model.Response;

import com.example.tweeter.Server.ModelObserver;

import java.util.List;

public class FollowManipulationResult {


    public boolean isNowFollowing() {
        return isNowFollowing;
    }

    public FollowManipulationResult(boolean isNowFollowing, List<ModelObserver> toUpdate) {
        this.isNowFollowing = isNowFollowing;
        this.toUpdate = toUpdate;
    }

    public List<ModelObserver> getToUpdate() {
        return toUpdate;
    }

    private boolean isNowFollowing;
    private List<ModelObserver> toUpdate;
}
