package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.services.FeedService;

public class FeedPresenter {

    public FeedResponse getFeed(FeedRequest req){

        return new FeedService().getFeed(req);
    }
    public FeedResponse getFeed(FeedRequest req, ServerFacade server){
        return new FeedService().getFeed(req, server);
    }
}
