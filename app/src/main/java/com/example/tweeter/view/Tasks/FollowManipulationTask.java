package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.presenter.FollowManipulationPresenter;

public class FollowManipulationTask extends AsyncTask<FollowManipulationRequest,Integer,Integer> {
    private FollowManipulationPresenter toUse;
    private Observer toCall;
    private FollowManipulationResult toReturn;

    public interface Observer {
        void setResults(FollowManipulationResult res);
    }

    public FollowManipulationTask(FollowManipulationPresenter pres, Observer toCall){
        this.toCall = toCall;
        this.toUse = pres;
    }

    @Override
    protected Integer doInBackground(FollowManipulationRequest... followManipulationRequests) {
        this.toReturn = toUse.manipulateFollow(followManipulationRequests[0]);
        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        for(ModelObserver o : toReturn.getToUpdate()){
            o.modelUpdated();
        }
        toCall.setResults(toReturn);
    }
}
