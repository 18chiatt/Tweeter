package com.example.tweeter.presenter;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.services.UserService;

public class UserPresenter {
    public UserResponse getUser(UserRequest r){

        UserService service = new UserService();
        return service.getUser(r);
    }

    public UserResponse getUser(UserRequest r, ServerFacade server){
        UserService service = new UserService();
        return service.getUser(r,server);
    }
}
