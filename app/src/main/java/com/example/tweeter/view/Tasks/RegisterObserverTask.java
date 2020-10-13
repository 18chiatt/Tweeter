package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.presenter.ObserverNotificationPresenter;

public class RegisterObserverTask extends AsyncTask<ModelObserver,Integer,Integer> {
    ObserverNotificationPresenter toUse;
    public RegisterObserverTask(ObserverNotificationPresenter toUse){
        this.toUse = toUse;
    }

    @Override
    protected Integer doInBackground(ModelObserver... modelObservers) {
        toUse.registerObserver(modelObservers[0]);
        return 3;
    }
}
