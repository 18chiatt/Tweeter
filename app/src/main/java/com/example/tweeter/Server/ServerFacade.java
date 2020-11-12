package com.example.tweeter.Server;

import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.model.request.UserStatsRequest;

public interface ServerFacade {
    FollowingResponse getFollowing(FollowingRequest req);
    FollowerResponse getFollowers(FollowerRequest req);
    LoginResponse login(LoginRequest req);
    UserStatsResponse getUserStats(UserStatsRequest req);
    StoryResponse getStory(StoryRequest req);
    UserResponse getUser(UserRequest req);
    FeedResponse getFeed(FeedRequest req);
    FollowingStatusResponse getFollowingStatus(FollowingStatusRequest req);
    FollowManipulationResult manipulateFollow(FollowManipulationRequest req);
    PostStatusResponse postStatus(PostStatusRequest req);
    RegisterResponse registerUser(RegisterRequest req);
    void registerObserver(ModelObserver obs);
    // remember to cleanse images when implementing this
    //give better implementation static data of the modelObservers and call it apropriately
    //use @expose with gson to set what gets serialized


    //@Expose(serialize = false)
    //private String lastName;
    //
    //@Expose (serialize = false, deserialize = false)
    //private String emailAddress;
    //
    //
    //

    //auth tokens are dealt with by singleton at server proxy level. they'll be added to appropriate
    //requests and scraped from appropriate responses
}
