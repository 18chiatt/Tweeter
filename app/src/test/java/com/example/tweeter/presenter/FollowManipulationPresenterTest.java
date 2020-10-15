package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FollowManipulationPresenterTest {

    FollowManipulationPresenter toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new FollowManipulationPresenter();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }


    @Test
    public void manipulateFollow() {
        FollowingRequest followingRequest = new FollowingRequest(user,Integer.MAX_VALUE,null);
        FollowingResponse resp = new FollowingPresenter().getFollowing(followingRequest);
        List<User> originalUsers = resp.getUsersTheyAreFollowing();
        for(User u : resp.getUsersTheyAreFollowing()){
            FollowManipulationRequest manipulationRequest = new FollowManipulationRequest(user,u,false); //show we can remove follows
            toUse.manipulateFollow(manipulationRequest);
        }
        FollowingRequest hopefullyEmptyRequest = new FollowingRequest(user,Integer.MAX_VALUE,null);
        FollowingResponse hopefullyEmptyResponse = new FollowingPresenter().getFollowing(followingRequest);

        assert(hopefullyEmptyResponse.getUsersTheyAreFollowing().size() == 0);

        //now user is not following people

        //show we can add all follows back

        for(User u : originalUsers){
            FollowManipulationRequest manipulationRequest = new FollowManipulationRequest(user,u,true);
            toUse.manipulateFollow(manipulationRequest);
        }
        FollowingRequest hopefullyOriginalRequest = new FollowingRequest(user,Integer.MAX_VALUE,null);
        FollowingResponse hopefullyOriginal = new FollowingPresenter().getFollowing(followingRequest);

        assertEquals(originalUsers,hopefullyOriginal.getUsersTheyAreFollowing());


    }
}