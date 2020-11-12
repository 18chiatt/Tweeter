package com.example.tweeter.services;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.Server.ServerFacade;
import com.example.tweeter.Server.ServerFake;
import com.example.tweeter.Server.ServerProxy;

public class ObserverNotificationService {

    public void registerObserver(ModelObserver obs){

        ServerFacade server =  new ServerProxy();
        server.registerObserver(obs);

    }

    public void registerObserver(ModelObserver obs, ServerFacade server){
        server.registerObserver(obs);

    }
}
