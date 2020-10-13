package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.request.UserStatsRequest;

public class UserStatsService {

    public UserStatsResponse getUserStats(UserStatsRequest req){
        ServerFacade server = new ServerFake(); //FIXME update this to use real server
        return server.getUserStats(req);
    }
}
