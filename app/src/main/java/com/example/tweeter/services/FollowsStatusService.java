package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.request.FollowingStatusRequest;

public class FollowsStatusService {

    public FollowingStatusResponse getFollowsStatus(FollowingStatusRequest req){
        ServerFacade server  = new ServerFake();
        return server.getFollowingStatus(req);
    }
}
