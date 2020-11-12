package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.services.FollowingService;
import com.example.tweeter.services.ImageService;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FollowingStatusPresenterTest {

    FollowingStatusPresenter toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new FollowingStatusPresenter();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();

        ImageService.loadImage(user);
        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }

    @Test
    public void getStatus() {
        List<User> allusers = server.getAll();
        FollowingService followingService = new FollowingService();

        for(User u : allusers){
            FollowingRequest req = new FollowingRequest(u,Integer.MAX_VALUE,null);
            FollowingResponse resp = followingService.getFollowing(req,new ServerFake());
            if(resp.getUsersTheyAreFollowing().contains(user)){
                FollowingStatusRequest request2 = new FollowingStatusRequest(u,user);
                assert(toUse.getStatus(request2,new ServerFake()).isFollows());
            } else {
                FollowingStatusRequest request2 = new FollowingStatusRequest(u,user);
                assert(!toUse.getStatus(request2,new ServerFake()).isFollows());
            }

        }


    }
}