package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;
import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.request.PostStatusRequest;

public class PostTweetService {

    public PostStatusResponse postTweet(PostStatusRequest req){

        ServerFacade server = new ServerProxy();
        return server.postStatus(req);
    }

    public PostStatusResponse postTweet(PostStatusRequest req, ServerFacade server){
        return server.postStatus(req);
    }

}
