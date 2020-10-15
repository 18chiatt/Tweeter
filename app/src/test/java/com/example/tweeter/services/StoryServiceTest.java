package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.StoryPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class StoryServiceTest {
    StoryService toUse;
    ServerFake server;
    User user;
    PostTweetService thingToCauseUpdate;

    @Before
    public void setup(){
        this.toUse = new StoryService();
        this.server = new ServerFake();
        thingToCauseUpdate = new PostTweetService();
        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }



    @Test
    public void getStory() {
        StoryRequest req = new StoryRequest(Integer.MAX_VALUE,user,null);
        StoryResponse resp = toUse.getStory(req);
        Set<String> messages = new TreeSet<>();

        for(Status s : resp.getTheStatus()){
            assert(s.getSaidBy().equals(user));
            assert(messages.contains(s.getMessage()) == false); //show that the messages feature works correctly
            messages.add(s.getMessage());
        }

        //show paginated results work
        List<Status> paginatedMessages = new ArrayList<>();

        StoryRequest paginated = new StoryRequest(1,user,null);
        StoryResponse paginatedResponse = toUse.getStory(paginated);
        paginatedMessages.addAll(paginatedResponse.getTheStatus());

        while(paginatedResponse.isHasMore()){
            paginated = new StoryRequest(1,user,paginatedMessages.get(paginatedMessages.size()-1));
            paginatedResponse = toUse.getStory(paginated);
            paginatedMessages.addAll(paginatedResponse.getTheStatus());
        }

        assertEquals(resp.getTheStatus(),paginatedMessages);

    }
}