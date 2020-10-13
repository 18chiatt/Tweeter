package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.presenter.FollowersPresenter;

public class FollowersTask extends AsyncTask<FollowerRequest,Integer,Integer> {
    Observer toCall;
    FollowerResponse response;
    FollowersPresenter presenter;
    public interface Observer {
        void addFollowers(FollowerResponse r);
    }
    public void setObserver(Observer set, FollowersPresenter toUse){
        toCall = set;
        presenter = toUse;
    }

    @Override
    protected Integer doInBackground(FollowerRequest... followerRequests) {
        if(toCall == null){
            System.out.println("Didn't set observer, this is a waste of time!");
        }

        if(presenter == null){
            System.out.println("Didn't set presenter!");
        }

        response = presenter.getFollowers(followerRequests[0]);

        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        toCall.addFollowers(response);
    }
}
