package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.services.RegisterService;

public class RegisterPresenter {
    public RegisterResponse register(RegisterRequest req){

        return new RegisterService().register(req);
    }

    public RegisterResponse register(RegisterRequest req, ServerFacade server){
        return new RegisterService().register(req ,server);
    }
}
