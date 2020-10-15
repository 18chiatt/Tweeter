package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.services.ImageService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FollowersPresenterTest {
    FollowersPresenter toUse;
    ServerFake server;
    User user;
    FollowingPresenter following;

    @Before
    public void setup(){
        this.toUse = new FollowersPresenter();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();

        ImageService.loadImage(user);
        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
        following = new FollowingPresenter();
    }


    @Test
    public void getFollowers() {
        FollowerRequest req = new FollowerRequest(user,Integer.MAX_VALUE,null);
        FollowerResponse resp = toUse.getFollowers(req);

        List<User> allAtOnce = new ArrayList<>(resp.getFollowers());
        List<User> iterative = new ArrayList<>();



        for(User u : resp.getFollowers()){ //check if everyone who follows is actually following
            FollowingRequest checkIfFollows = new FollowingRequest(u,Integer.MAX_VALUE,null);
            FollowingResponse response = following.getFollowing(checkIfFollows);
            assert(response .getUsersTheyAreFollowing().contains(user));
        }

        FollowerRequest newRequest = new FollowerRequest(user,1,null); //check if paginated results works properly
        FollowerResponse newReponse = toUse.getFollowers(newRequest);
        iterative.addAll(newReponse.getFollowers());
        while(newReponse.hasMore()){
            newRequest = new FollowerRequest(user,1,newReponse.getFollowers().get(0));
            newReponse = toUse.getFollowers(newRequest);
            assert(newReponse.getFollowers().size() <= 1);
            iterative.addAll(newReponse.getFollowers());
        }

        assertEquals(allAtOnce,iterative);
        assert(allAtOnce.size() > 5);



    }
}