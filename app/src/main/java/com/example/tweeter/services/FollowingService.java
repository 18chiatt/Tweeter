package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowingRequest;

public class FollowingService {

    public FollowingResponse getFollowing (FollowingRequest req){

        ServerFacade server =  new ServerProxy(); //FIXME update this to use real server
        System.out.println("Getting followers of " + req.getPersonWhoFollows().toString());
        FollowingResponse resp = server.getFollowing(req);

        for(User u : resp.getUsersTheyAreFollowing()){
            ImageService.loadImage(u);
        }

        return resp;
    }

    public FollowingResponse getFollowing (FollowingRequest req, ServerFacade server){

        System.out.println("Getting followers of " + req.getPersonWhoFollows().toString());
        FollowingResponse resp = server.getFollowing(req);

        for(User u : resp.getUsersTheyAreFollowing()){
            ImageService.loadImage(u);
        }

        return resp;
    }
}

