package com.example.tweeter.presenter;

import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.services.FollowerService;
import com.example.tweeter.services.FollowingService;

public class FollowingPresenter {

    public FollowingResponse getFollowing(FollowingRequest req){
        return new FollowingService().getFollowing(req);
    }
}
