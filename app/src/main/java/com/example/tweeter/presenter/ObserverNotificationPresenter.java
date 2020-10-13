package com.example.tweeter.presenter;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.services.ObserverNotificationService;

public class ObserverNotificationPresenter {
    public void registerObserver(ModelObserver toNotify){
        new ObserverNotificationService().registerObserver(toNotify);
    }
}