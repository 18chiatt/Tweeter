package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.model.request.UserRequest;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateFakeData {
    private ServerFake server;

    @Before
    public void init(){
        server = new ServerFake();

        RegisterRequest reg = new RegisterRequest("username","password","Chase","Hiatt",null);
        server.registerUser(reg);
        User thePerson = server.login(new LoginRequest("username","password")).getLoggedInAs();
        System.out.println(thePerson);
        FollowerRequest req = new FollowerRequest(thePerson,20,null);
        FollowingRequest req2 = new FollowingRequest(thePerson,20,null);

        FollowingResponse peopleTheyAreFollowingResp = server.getFollowing(req2);
        System.out.println(peopleTheyAreFollowingResp.getUsersTheyAreFollowing().toString());
        System.out.println("");

        for (User u : peopleTheyAreFollowingResp.getUsersTheyAreFollowing()){
            System.out.println(server.getStory(new StoryRequest(5,u,null)).getTheStatus().toString());
        }
    }

    @Test
    public void getJSON(){

        try {
            FileWriter out = new FileWriter("output.txt");

            out.write(new GsonBuilder().create().toJson(server));
            out.close();
            String current = new java.io.File( "." ).getCanonicalPath();
            System.out.println("Current dir:"+current);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getJSONT(){
        UserRequest req = new UserRequest("username");
        System.out.println(new GsonBuilder().create().toJson(req));
    }

    

}
