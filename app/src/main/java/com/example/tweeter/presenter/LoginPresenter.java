package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.services.LoginService;

public class LoginPresenter {


    public LoginResponse login(LoginRequest req){

        LoginService toUse = new LoginService();
        return toUse.login(req);
    }

    public LoginResponse login(LoginRequest req, ServerFacade server){
        LoginService toUse = new LoginService();
        return toUse.login(req,server);
    }
}
