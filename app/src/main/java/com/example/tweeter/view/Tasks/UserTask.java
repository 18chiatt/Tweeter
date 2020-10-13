package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.UserPresenter;

public class UserTask extends AsyncTask<UserRequest,Integer,Integer> {
    UserPresenter presenter;
    Observer obs;
    UserResponse resp;

    public UserTask(UserPresenter pres, Observer toCall){
        this.presenter = pres;
        this.obs = toCall;
    }

    public interface Observer {
        void updateUser(UserResponse r);
    }
    @Override
    protected Integer doInBackground(UserRequest... userRequests) {
        this.resp = presenter.getUser(userRequests[0]);
        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        obs.updateUser(resp);
    }
}
