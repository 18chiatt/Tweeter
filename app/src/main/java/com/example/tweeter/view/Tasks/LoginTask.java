package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.presenter.LoginPresenter;

public class LoginTask extends AsyncTask<LoginRequest, Integer, Integer> {
    private LoginPresenter thePresenter;
    private LoginResponse result;
    private Observer toCall;
    public interface Observer {
        void loginSuccessful(LoginResponse loginResponse);
        void loginUnsuccessful(LoginResponse loginResponse);
        void handleException(Exception ex);
    }
    public LoginTask (LoginPresenter presenter, Observer obs){
        this.thePresenter = presenter;
        this.toCall = obs;
    }

    @Override
    protected Integer doInBackground(LoginRequest... loginRequests) {
        result = thePresenter.login(loginRequests[0]);
        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(toCall == null){
            return;
        }
        if(result.isSuccess()){
            toCall.loginSuccessful(result);
        } else {
            toCall.loginUnsuccessful(result);
        }
    }
}
