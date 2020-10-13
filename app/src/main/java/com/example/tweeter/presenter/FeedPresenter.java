package com.example.tweeter.presenter;

import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.services.FeedService;

public class FeedPresenter {

    public FeedResponse getFeed(FeedRequest req){
        return new FeedService().getFeed(req);
    }
}
