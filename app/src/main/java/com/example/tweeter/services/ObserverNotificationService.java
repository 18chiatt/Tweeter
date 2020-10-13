package com.example.tweeter.services;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;

public class ObserverNotificationService {

    public void registerObserver(ModelObserver obs){
        ServerFacade server = new ServerFake();
        server.registerObserver(obs);

    }
}
