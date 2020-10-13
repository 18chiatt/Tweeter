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

    private List<ModelObserver> toUpdate;

}
