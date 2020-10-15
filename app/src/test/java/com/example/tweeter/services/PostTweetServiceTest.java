package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.PostStatusPresenter;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class PostTweetServiceTest {

    PostTweetService toUse;
    ServerFake server;
    User user;

    @Before
    public void setup(){
        this.toUse = new PostTweetService();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();

        ImageService.loadImage(user);
        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }

    @Test
    public void postStatus() {
        Status theStatus = new Status("This is a new Message", Instant.now(),user);
        PostStatusRequest req = new PostStatusRequest(theStatus);
        StoryRequest feedRequest = new StoryRequest(Integer.MAX_VALUE, user,null);
        StoryResponse feedResponse = server.getStory(feedRequest);
        assert(!feedResponse.getTheStatus().contains(theStatus)); //show this post isn't contained

        toUse.postTweet(req);

        feedResponse = server.getStory(feedRequest);

        assert(feedResponse.getTheStatus().contains(theStatus)); //now the post is there

        FollowerRequest followerRequest = new FollowerRequest(user,Integer.MAX_VALUE,null);
        FollowerResponse followerResponse = server.getFollowers(followerRequest);

        //show it is now in the feed of everyone following them

        for(User u : server.getAll()){
            FollowingRequest followingRequest = new FollowingRequest(u,Integer.MAX_VALUE,null);
            FollowingResponse theirResponse = server.getFollowing(followingRequest);
            FeedRequest feed = new FeedRequest(u,Integer.MAX_VALUE,null); //if a user is following, their feed contains the new status, else it doesn't
            if(theirResponse.getUsersTheyAreFollowing().contains(user)){

                assert(server.getFeed(feed).getTheStatus().contains(theStatus));
            } else {
                assert(!server.getFeed(feed).getTheStatus().contains(theStatus));
            }
        }



    }
}