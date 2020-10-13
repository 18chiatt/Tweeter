package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;

public class LoginService {
    public LoginResponse login (LoginRequest req){
        ServerFacade server = new ServerFake();
        LoginResponse resp = server.login(req);
        if(resp.isSuccess())
            ImageService.loadImage(resp.getLoggedInAs());
        return resp;
    }
}
