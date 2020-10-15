package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.generators.UserGenerator;
import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.UserPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService toUse;
    ServerFake server;
    User user;
    PostTweetService thingToCauseUpdate;

    @Before
    public void setup(){
        this.toUse = new UserService();
        this.server = new ServerFake();
        thingToCauseUpdate = new PostTweetService();
        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }
    @Test
    public void getUser() {
        for(User u : server.getAll()){
            UserRequest req = new UserRequest(u.getUserName());
            UserResponse resp = toUse.getUser(req);
            assertEquals(u,resp.getToRespondWith());
        }

        for(int i=0; i< 1000; i++){
            User nonExistingUser = UserGenerator.getUser();
            UserRequest badRequest = new UserRequest(nonExistingUser.getUserName());
            UserResponse badResponseHopefully = toUse.getUser(badRequest);
            assert(badResponseHopefully.isSuccess() == false);
        }
    }
}