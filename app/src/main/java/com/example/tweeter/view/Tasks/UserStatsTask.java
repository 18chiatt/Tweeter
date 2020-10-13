package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.request.UserStatsRequest;
import com.example.tweeter.presenter.UserStatsPresenter;
import com.example.tweeter.services.UserStatsService;

public class UserStatsTask extends AsyncTask<UserStatsRequest,Integer,Integer> {
    private UserStatsPresenter service;
    private UserStatsResponse toRespondWith;
    Observer toCall;

    public UserStatsTask(UserStatsPresenter service, Observer toCall){
        this.service = service;
        this.toCall = toCall;
    }

    @Override
    protected Integer doInBackground(UserStatsRequest... userStatsRequests) {
        toRespondWith = this.service.getUserStats(userStatsRequests[0]);

        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        toCall.updateValues(toRespondWith);
    }

    public interface Observer{
        void updateValues(UserStatsResponse resp);
    }
}
