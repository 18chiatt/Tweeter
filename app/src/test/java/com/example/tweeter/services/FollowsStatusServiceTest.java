package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.FollowingStatusPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FollowsStatusServiceTest {

    FollowsStatusService toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new FollowsStatusService();
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
            FollowingResponse resp = followingService.getFollowing(req);
            if(resp.getUsersTheyAreFollowing().contains(user)){
                FollowingStatusRequest request2 = new FollowingStatusRequest(u,user);
                assert(toUse.getFollowsStatus(request2).isFollows());
            } else {
                FollowingStatusRequest request2 = new FollowingStatusRequest(u,user);
                assert(!toUse.getFollowsStatus(request2).isFollows());
            }

        }


    }
}