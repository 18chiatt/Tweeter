package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFake;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FollowingPresenterTest {

    FollowingPresenter toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new FollowingPresenter();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();

        ImageService.loadImage(user);
        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }

    @Test
    public void getFollowing() {
        FollowingRequest req = new FollowingRequest(user,Integer.MAX_VALUE,null);
        FollowingResponse allFollowing = toUse.getFollowing(req);
        List<User> allAtOnce = new ArrayList<>(allFollowing.getUsersTheyAreFollowing());
        assert(allFollowing.getUsersTheyAreFollowing().size() > 1);

        for(User u : allFollowing.getUsersTheyAreFollowing()){ //check if everyone they are following has them as follower
            FollowerRequest commutativeCheck = new FollowerRequest(u,Integer.MAX_VALUE,null);
            List<User> theUsersThePersonIsFollowing = new FollowersPresenter().getFollowers(commutativeCheck).getFollowers();
            assert(theUsersThePersonIsFollowing.contains(user));

        }

        //check paginated results
        List<User> iterativeFollowing = new ArrayList<>();
        FollowingRequest iterativeRequest = new FollowingRequest(user,1,null);
        FollowingResponse iterativeResponse = toUse.getFollowing(iterativeRequest);
        iterativeFollowing.addAll(iterativeResponse.getUsersTheyAreFollowing());

        while(iterativeResponse.hasMore()){
           iterativeRequest = new FollowingRequest(user,1,iterativeFollowing.get(iterativeFollowing.size()-1));
            iterativeResponse = toUse.getFollowing(iterativeRequest);
            iterativeFollowing.addAll(iterativeResponse.getUsersTheyAreFollowing());
        }

        assertEquals(allAtOnce,iterativeFollowing);

    }
}