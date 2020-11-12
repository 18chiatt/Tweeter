package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.request.UserRequest;

public class UserService {
    public UserResponse getUser(UserRequest r){

        ServerFacade fake =  new ServerProxy();
        UserResponse resp = fake.getUser(r);
        if(resp.isSuccess()) {
            ImageService.loadImage(resp.getToRespondWith());
        }
        return resp;
    }

    public UserResponse getUser(UserRequest r, ServerFacade server){
        ServerFacade fake = new ServerFake();
        UserResponse resp = fake.getUser(r);
        if(resp.isSuccess()) {
            ImageService.loadImage(resp.getToRespondWith());
        }
        return resp;
    }
}
