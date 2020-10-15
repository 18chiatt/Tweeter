package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.model.request.UserStatsRequest;
import com.example.tweeter.presenter.UserStatsPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStatsServiceTest {
    UserStatsService toUse;
    ServerFake server;
    User user;
    PostTweetService thingToCauseUpdate;

    @Before
    public void setup(){
        this.toUse = new UserStatsService();
        this.server = new ServerFake();
        thingToCauseUpdate = new PostTweetService();
        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }
    @Test
    public void getUserStats() {
        int index =0;

        for(User u : server.getAll()){
            index++;
            UserStatsRequest req = new UserStatsRequest(u);
            UserStatsResponse resp = toUse.getUserStats(req);

            FollowerRequest followerRequest = new FollowerRequest(u,Integer.MAX_VALUE,null);
            FollowerResponse followerResponse = server.getFollowers(followerRequest);

            assert(followerResponse.getFollowers().size() == resp.getNumFollowers());

            FollowingRequest followingRequest = new FollowingRequest(u,Integer.MAX_VALUE,null);
            FollowingResponse followingResponse = server.getFollowing(followingRequest);

            assert(followingResponse.getUsersTheyAreFollowing().size() == resp.getNumPeopleTheyAreFollowing());
        }

    }
}