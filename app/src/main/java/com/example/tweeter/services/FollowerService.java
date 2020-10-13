package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowerRequest;

public class FollowerService {

    public FollowerResponse getFollowers(FollowerRequest req){
        ServerFacade server = new ServerFake(); //FIXME update this to use real server
        FollowerResponse resp = server.getFollowers(req);

        for(User u : resp.getFollowers()){
            ImageService.loadImage(u);
        }

        return resp;
    }
}
