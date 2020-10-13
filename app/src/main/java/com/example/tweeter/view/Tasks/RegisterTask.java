package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.presenter.RegisterPresenter;

public class RegisterTask extends AsyncTask<RegisterRequest,Integer,Integer> {
    private Observer toCall;
    private RegisterPresenter toUse;
    private RegisterResponse toReturn;

    public interface Observer {
        public void success();
        public void fail();
    }

    @Override
    protected Integer doInBackground(RegisterRequest... registerRequests) {
        this.toReturn = toUse.register(registerRequests[0]);
        return 3;
    }

    public RegisterTask(Observer toCall, RegisterPresenter toUse) {
        this.toCall = toCall;
        this.toUse = toUse;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(toReturn.isWasSuccessful()){
            toCall.success();
        } else {
            toCall.fail();
        }
    }
}
