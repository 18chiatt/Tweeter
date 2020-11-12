package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.request.UserStatsRequest;
import com.example.tweeter.services.UserStatsService;

public class UserStatsPresenter {

    public UserStatsResponse getUserStats(UserStatsRequest req){

        UserStatsService toUse = new UserStatsService();
        return toUse.getUserStats(req);

    }

    public UserStatsResponse getUserStats(UserStatsRequest req, ServerFacade server){
        UserStatsService toUse = new UserStatsService();
        return toUse.getUserStats(req, server);

    }



}
