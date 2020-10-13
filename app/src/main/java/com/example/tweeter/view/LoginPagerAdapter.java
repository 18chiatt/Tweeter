package com.example.tweeter.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.tweeter.model.domain.User;
import com.example.tweeter.view.fragments.Login.LoginFragment;
import com.example.tweeter.view.fragments.Login.RegisterFragment;
import com.example.tweeter.view.fragments.StatusLists.FeedFragment;
import com.example.tweeter.view.fragments.StatusLists.StoryFragment;
import com.example.tweeter.view.fragments.UserLists.FollowersFragment;
import com.example.tweeter.view.fragments.UserLists.FollowingFragment;

public class LoginPagerAdapter extends FragmentStatePagerAdapter {
    private User theUser;

    final int pageCount = 2;
    private String[] tabTitles = new String[]{"Login","Register"};


    public LoginPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setUser(User toSet){
        this.theUser = toSet;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();

        }
        System.out.println("SOmething went very wrong!!!!");
        return new RegisterFragment();
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}