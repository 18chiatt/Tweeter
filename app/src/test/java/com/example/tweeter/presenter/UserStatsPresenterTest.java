package com.example.tweeter.presenter;

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
import com.example.tweeter.services.AuthTokenHolder;
import com.example.tweeter.services.PostTweetService;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserStatsPresenterTest {

    UserStatsPresenter toUse;
    ServerFake server;
    User user;
    PostTweetService thingToCauseUpdate;

    @Before
    public void setup(){
        this.toUse = new UserStatsPresenter();
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
            UserStatsRequest req = new UserStatsRequest(u, AuthTokenHolder.authToken,user);
            UserStatsResponse resp = toUse.getUserStats(req,new ServerFake());

            FollowerRequest followerRequest = new FollowerRequest(u,Integer.MAX_VALUE,null);
            FollowerResponse followerResponse = server.getFollowers(followerRequest);

            assert(followerResponse.getFollowers().size() == resp.getNumFollowers());

            FollowingRequest followingRequest = new FollowingRequest(u,Integer.MAX_VALUE,null);
            FollowingResponse followingResponse = server.getFollowing(followingRequest);

            assert(followingResponse.getUsersTheyAreFollowing().size() == resp.getNumPeopleTheyAreFollowing());
        }

    }
}