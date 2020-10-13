package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.presenter.FollowingPresenter;

public class FollowingTask extends AsyncTask<FollowingRequest,Integer,Integer> {
    FollowingPresenter toUse;
    Observer toUpdate;
    FollowingResponse toRespondWith;

    public FollowingTask(Observer obs, FollowingPresenter toCall){
        this.toUpdate = obs;
        this.toUse = toCall;
    }

    @Override
    protected Integer doInBackground(FollowingRequest... followingRequests) {
        toRespondWith = toUse.getFollowing(followingRequests[0]);
        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        for(User u : toRespondWith.getUsersTheyAreFollowing()){
            System.out.println("Task is getting User " + u.toString());
        }
        toUpdate.addFollowing(toRespondWith);
    }

    public interface Observer {
        void addFollowing(FollowingResponse res);
    }

}
