package com.example.tweeter.view.fragments.StatusLists;

import android.content.Intent;

import com.example.tweeter.model.domain.User;

public interface IntentFulfiller {
    void startPersonActivity(User userToView);
    void startActivity(Intent intent);
}
