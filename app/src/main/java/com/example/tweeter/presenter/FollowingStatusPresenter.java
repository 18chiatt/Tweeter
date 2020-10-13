package com.example.tweeter.presenter;

import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.services.FollowsStatusService;

public class FollowingStatusPresenter {
    public FollowingStatusResponse getStatus(FollowingStatusRequest req){
        return new FollowsStatusService().getFollowsStatus(req);
    }
}
