package com.example.tweeter.services;

import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.FeedPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FeedServiceTest {

    FeedService toUse;
    ServerFake server;
    User user;

    @Before
    public void setup() {
        this.toUse = new FeedService();
        this.server = new ServerFake();
        server.clearAll();
        server = new ServerFake();

        ImageService.loadImage(user);
        server.registerUser(new RegisterRequest("Dank", "Password", "Chase", "Hiatt", null));
        server.login(new LoginRequest("Dank", "Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }

    @Test
    public void getFeed() {
        FeedRequest req = new FeedRequest(user, 6, null);
        FeedResponse resp = toUse.getFeed(req,new ServerFake());
        List<Status> allStatus = new ArrayList<>();
        List<Status> iterativelyBuilt = new ArrayList<>();
        allStatus.addAll(toUse.getFeed(new FeedRequest(user, 999999, null),new ServerFake()).getTheStatus());
        assert (resp.getTheStatus().size() <= 6);
        iterativelyBuilt.addAll(resp.getTheStatus());

        while (resp.isHasMore()) {
            resp = toUse.getFeed(new FeedRequest(user, 5, resp.getTheStatus().get(resp.getTheStatus().size() - 1)),new ServerFake());

            for (Status s : resp.getTheStatus()) {

                assert (!iterativelyBuilt.contains(s));

            }
            iterativelyBuilt.addAll(resp.getTheStatus());
        }
        System.out.println(iterativelyBuilt.size());
        System.out.println(allStatus.size());

        for (int i = 0; i < allStatus.size(); i++) {
            if (!allStatus.get(i).equals(iterativelyBuilt.get(i))) {

            }
            assertEquals(allStatus.get(i), iterativelyBuilt.get(i));
        }
        assert (iterativelyBuilt.size() == allStatus.size());
        System.out.println(allStatus.size());
        assert (allStatus.size() > 10);


    }

}