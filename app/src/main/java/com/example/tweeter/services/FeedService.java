package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.request.FeedRequest;

public class FeedService {

    public FeedResponse getFeed(FeedRequest req){

        ServerFacade server = new ServerProxy(); //FIXME update to use real server
        FeedResponse toRespondWith = server.getFeed(req);

        for(Status s : toRespondWith.getTheStatus()){
            ImageService.loadImage(s.getSaidBy());
        }
        return toRespondWith;

    }

    public FeedResponse getFeed(FeedRequest req, ServerFacade toUse){
        FeedResponse toRespondWith = toUse.getFeed(req);

        for(Status s : toRespondWith.getTheStatus()){
            ImageService.loadImage(s.getSaidBy());
        }
        return toRespondWith;
    }

    //get serverFake mock

}
