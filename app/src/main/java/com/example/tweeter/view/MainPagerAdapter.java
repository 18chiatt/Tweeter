package com.example.tweeter.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.tweeter.model.domain.User;
import com.example.tweeter.view.fragments.StatusLists.FeedFragment;
import com.example.tweeter.view.fragments.UserLists.FollowersFragment;
import com.example.tweeter.view.fragments.UserLists.FollowingFragment;
import com.example.tweeter.view.fragments.StatusLists.StoryFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private User theUser;

    final int pageCount = 4;
    private String[] tabTitles = new String[]{"Feed","Story","Following","Followers"};

    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setUser(User toSet){
        this.theUser = toSet;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FeedFragment(theUser);
            case 1:
                return new StoryFragment(theUser);
            case 2:
                return new FollowingFragment(theUser);
            case 3:
                return new FollowersFragment(theUser);

        }
        System.out.println("SOmething went very wrong!!!!");
        return new FeedFragment();
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
