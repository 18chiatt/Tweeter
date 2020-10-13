package com.example.tweeter.presenter;

import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.services.PostTweetService;

public class PostStatusPresenter {

    public PostStatusResponse postStatus(PostStatusRequest req){
        return new PostTweetService().postTweet(req);
    }
}
