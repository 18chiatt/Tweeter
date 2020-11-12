package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.request.StoryRequest;

public class StoryService {

    public StoryResponse getStory(StoryRequest req){

        ServerFacade theServer =  new ServerProxy(); //FIXME make this use real server class
        StoryResponse resp =  theServer.getStory(req);

        for(Status s : resp.getTheStatus()){
            ImageService.loadImage(s.getSaidBy());
        }
        return resp;
    }

    public StoryResponse getStory(StoryRequest req, ServerFacade theServer){

        StoryResponse resp =  theServer.getStory(req);

        for(Status s : resp.getTheStatus()){
            ImageService.loadImage(s.getSaidBy());
        }
        return resp;
    }

}
