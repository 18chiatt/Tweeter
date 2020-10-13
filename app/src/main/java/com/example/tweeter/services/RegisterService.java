package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.request.RegisterRequest;

public class RegisterService {

    public RegisterResponse register(RegisterRequest req){
        ServerFacade server = new ServerFake();
        return server.registerUser(req);
    }
}
