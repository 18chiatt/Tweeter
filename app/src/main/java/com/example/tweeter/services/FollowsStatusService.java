package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.request.FollowingStatusRequest;

public class FollowsStatusService {

    public FollowingStatusResponse getFollowsStatus(FollowingStatusRequest req){

        ServerFacade server  =  new ServerProxy();
        return server.getFollowingStatus(req);
    }

    public FollowingStatusResponse getFollowsStatus(FollowingStatusRequest req, ServerFacade server){

        return server.getFollowingStatus(req);
    }
}
