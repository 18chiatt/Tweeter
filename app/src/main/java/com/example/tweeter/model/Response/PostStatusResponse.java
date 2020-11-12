package com.example.tweeter.model.Response;

import com.example.tweeter.Server.ModelObserver;

import java.util.List;

public class PostStatusResponse {
    public PostStatusResponse(List<ModelObserver> toUpdate) {
        this.toUpdate = toUpdate;
    }

    public List<ModelObserver> getToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(List<ModelObserver> toUpdate) {
        this.toUpdate = toUpdate;
    }

    private List<ModelObserver> toUpdate;

    public boolean isWasSuccess() {
        return wasSuccess;
    }

    private boolean wasSuccess;

}
