package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FollowManipulationService {
    public FollowManipulationResult manipulateFollows(FollowManipulationRequest req){

        ServerFacade server =  new ServerProxy();
        return server.manipulateFollow(req);
    }

    public FollowManipulationResult manipulateFollows(FollowManipulationRequest req, ServerFacade server){

        return server.manipulateFollow(req);
    }
}
