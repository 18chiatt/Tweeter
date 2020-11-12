package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.services.FollowerService;

public class FollowersPresenter {

    public FollowerResponse getFollowers(FollowerRequest req){


        return new FollowerService().getFollowers(req);

    }

    public FollowerResponse getFollowers(FollowerRequest req, ServerFacade server){
        return new FollowerService().getFollowers(req, server);

    }
}
