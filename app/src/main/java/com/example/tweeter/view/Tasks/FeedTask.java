package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.presenter.FeedPresenter;

public class FeedTask extends AsyncTask<FeedRequest,Integer,Integer> {
    Observer toCall;
    FeedPresenter toUse;
    FeedResponse toReturn;

    public interface Observer {
        void updateModel(FeedResponse resp);
    }

    public FeedTask(Observer obs,FeedPresenter pres){
        this.toCall = obs;
        this.toUse = pres;

    }

    @Override
    protected Integer doInBackground(FeedRequest... feedRequests) {

        this.toReturn = toUse.getFeed(feedRequests[0]);
        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        toCall.updateModel(toReturn);
    }
}
