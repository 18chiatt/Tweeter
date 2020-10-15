package com.example.tweeter.view.fragments.StatusLists;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.presenter.UserPresenter;
import com.example.tweeter.view.Tasks.UserTask;

public class UsernameSpan extends ClickableSpan implements UserTask.Observer {
    String alias;
    Context context;
    IntentFulfiller fulfiller;
    private User theUser;

    public UsernameSpan(String alias, Context context, IntentFulfiller fulfiller){
        this.alias = alias;
        this.context = context;
        this.fulfiller = fulfiller;
    }

    @Override
    public void onClick(@NonNull View widget) {
        System.out.println("Called something!");
        UserTask task = new UserTask(new UserPresenter(),this);
        task.execute(new UserRequest(alias));

    }

    @Override
    public void updateUser(UserResponse r) {
        this.theUser = r.getToRespondWith();
        if(!r.isSuccess()){
            Toast.makeText(context,"Unable to find User!",Toast.LENGTH_LONG).show();
            this.theUser = r.getToRespondWith();

        } else {

            launchPersonActivity();
        }
    }

    private void launchPersonActivity(){

        fulfiller.startPersonActivity(theUser);

    }
}
