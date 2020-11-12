package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.request.RegisterRequest;

public class RegisterService {

    public RegisterResponse register(RegisterRequest req){

        ServerFacade server =  new ServerProxy();
        return server.registerUser(req);
    }

    public RegisterResponse register(RegisterRequest req, ServerFacade server){

        return server.registerUser(req);
    }
}
