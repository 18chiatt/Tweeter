package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.request.FollowManipulationRequest;

public class FollowManipulationService {
    public FollowManipulationResult manipulateFollows(FollowManipulationRequest req){
        ServerFacade server = new ServerFake();
        return server.manipulateFollow(req);
    }
}
