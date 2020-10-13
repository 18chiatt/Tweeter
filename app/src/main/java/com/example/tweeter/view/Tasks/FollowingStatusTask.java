package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.presenter.FollowingStatusPresenter;

public class FollowingStatusTask extends AsyncTask<FollowingStatusRequest,Integer,Integer> {
    private FollowingStatusResponse toRespond;
    Observer toCall;
    FollowingStatusPresenter toUse;
    public interface Observer {
        void update(FollowingStatusResponse resp);
    }

    public FollowingStatusTask(Observer toCall, FollowingStatusPresenter toUse){
        this.toCall = toCall;
        this.toUse = toUse;
    }
    @Override
    protected Integer doInBackground(FollowingStatusRequest... followingStatusRequests) {
        this.toRespond = toUse.getStatus(followingStatusRequests[0]);
        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        toCall.update(toRespond);
    }
}
