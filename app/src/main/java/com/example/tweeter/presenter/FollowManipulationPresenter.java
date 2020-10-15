package com.example.tweeter.presenter;

import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.services.FollowManipulationService;

public class FollowManipulationPresenter {
    public FollowManipulationResult manipulateFollow(FollowManipulationRequest req){
        return new FollowManipulationService().manipulateFollows(req);
    }
}
