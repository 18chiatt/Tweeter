package com.example.tweeter.presenter;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.services.PostTweetService;
import com.example.tweeter.view.Tasks.PostStatusTask;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class ObserverNotificationPresenterTest {
    ObserverNotificationPresenter toUse;
    ServerFake server;
    User user;
    PostTweetService thingToCauseUpdate;

    @Before
    public void setup(){
        this.toUse = new ObserverNotificationPresenter();
        this.server = new ServerFake();
        thingToCauseUpdate = new PostTweetService();
        server.clearAll();
        server = new ServerFake();


        server.registerUser(new RegisterRequest("Dank","Password","Chase","Hiatt", null));
        server.login(new LoginRequest("Dank","Man"));
        user = server.getUser(new UserRequest("Dank")).getToRespondWith();
    }

    private class NotifyMe implements ModelObserver{
        public int modelUpdated = 0;
        @Override
        public void modelUpdated() {
            modelUpdated = modelUpdated+1;
        }
    }
    @Test
    public void registerObserver() {

        NotifyMe observer = new NotifyMe();

        assert(observer.modelUpdated == 0);

        toUse.registerObserver(observer,new ServerFake()); //observer successfully registers with server
        server.notifyObservers();

        assert(observer.modelUpdated == 1);

        for(int i=0; i< 5; i++){
            server.notifyObservers();
            assert(observer.modelUpdated == i+2);
        }


    }
}

