package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.services.StoryService;

public class StoryPresenter {

    public StoryResponse getStory(StoryRequest request){

        StoryService serv = new StoryService();
        return serv.getStory(request);
    }

    public StoryResponse getStory(StoryRequest request, ServerFacade server){
        StoryService serv = new StoryService();
        return serv.getStory(request, server);
    }

}
