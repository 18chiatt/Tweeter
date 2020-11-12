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

public class PersonPagerAdapter extends FragmentStatePagerAdapter {
    private User theUser;
    private User loggedInAs;

    final int pageCount = 3;
    private String[] tabTitles = new String[]{"Story","Following","Followers"};

    public PersonPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setUser(User toSet, User loggedInAs){
        this.theUser = toSet;
        this.loggedInAs = loggedInAs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new StoryFragment(theUser,loggedInAs);
            case 1:
                return new FollowingFragment(theUser,loggedInAs);
            case 2:
                return new FollowersFragment(theUser,loggedInAs);

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
