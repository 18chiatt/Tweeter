package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.LoginPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginServiceTest {

    LoginService toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new LoginService();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }
    @Test
    public void login() {
        LoginRequest req = new LoginRequest("Dank","Password");
        assert(toUse.login(req,new ServerFake()).isSuccess());
        assert(toUse.login(req,new ServerFake()).getLoggedInAs().equals(user));

        req = new LoginRequest("UNKNOWN","lol");
        assert(!toUse.login(req,new ServerFake()).isSuccess());

        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("UNKNOWN","lol","Chase","Hiatt", null));
        server.login(new LoginRequest("UNKNOWN","lol"));

        assert(toUse.login(req,new ServerFake()).isSuccess());




    }
}