package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.generators.UserGenerator;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.RegisterPresenter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterServiceTest {

    RegisterService toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new RegisterService();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();

        ImageService.loadImage(user);

    }
    @Test
    public void register() {

        UserRequest userRequest = new UserRequest("Dank");
        assert(server.getUser(userRequest).isSuccess() == false); //user is not found prior to registration


        toUse.register(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();

        assert(server.getUser(userRequest).isSuccess() == true); //now user is found
        assert(server.getUser(userRequest).getToRespondWith().equals(user));

        assert(server.getUser(new UserRequest("Epic")).isSuccess() == false);

        toUse.register(new RegisterRequest("Epic","Password","Epic","Cookie",null)); //multiple users can be registered

        assert(server.getUser(new UserRequest("Epic")).isSuccess() == true);



        for(int i=0; i< 1000; i++){ //show we can register 1000 random users
            User newUser = UserGenerator.getUser();
            assert(server.getUser(new UserRequest(newUser.getUserName())).isSuccess() == false);
            toUse.register(new RegisterRequest(newUser.getUserName(),"ASDF",newUser.getFirstName(),newUser.getLastName(),null));
            assert(server.getUser(new UserRequest(newUser.getUserName())).isSuccess() == true);
        }


    }
}