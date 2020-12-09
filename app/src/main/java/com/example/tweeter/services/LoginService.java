package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;

public class LoginService {
    public LoginResponse login (LoginRequest req){

        ServerFacade server =  new ServerProxy();
        LoginResponse resp = server.login(req);
        AuthTokenHolder.setAuthToken(resp.getAuthToken());
        System.out.println("AuthToken: "+ AuthTokenHolder.authToken);
        if(resp.isSuccess())
            ImageService.loadImage(resp.getLoggedInAs());
        return resp;
    }

    public LoginResponse login (LoginRequest req, ServerFacade server){

        LoginResponse resp = server.login(req);
        AuthTokenHolder.setAuthToken(resp.getAuthToken());
        System.out.println("AuthToken: "+ AuthTokenHolder.authToken);
        if(resp.isSuccess())
            ImageService.loadImage(resp.getLoggedInAs());
        return resp;
    }
}
