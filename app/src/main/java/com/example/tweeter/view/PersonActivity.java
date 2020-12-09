package com.example.tweeter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.tweeter.R;
import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.model.request.UserStatsRequest;
import com.example.tweeter.presenter.FollowingStatusPresenter;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.presenter.UserStatsPresenter;
import com.example.tweeter.presenter.FollowManipulationPresenter;
import com.example.tweeter.services.AuthTokenHolder;
import com.example.tweeter.view.Tasks.FollowManipulationTask;
import com.example.tweeter.view.Tasks.FollowingStatusTask;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.Tasks.UserStatsTask;
import com.example.tweeter.view.fragments.StatusLists.FeedFragment;
import com.example.tweeter.view.util.ImageUtils;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.tweeter.view.fragments.StatusLists.FeedFragment.LOGGED_IN_AS_KEY;

public class PersonActivity extends AppCompatActivity implements UserStatsTask.Observer, FollowingStatusTask.Observer, FollowManipulationTask.Observer, ModelObserver {
    private TextView firstLastName;
    private TextView alias;
    private TextView numFollowers;
    private TextView numPeopleFollowing;

    private ImageView profilePicture;
    private Button followButton;
    User loggedInAs;
    User viewing;

    TabLayout tabs;
    ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegisterObserverTask task4 = new RegisterObserverTask(new ObserverNotificationPresenter());
        task4.execute(this);
        setContentView(R.layout.person_activity);
        //need a user who is logged in, the user who is selected
        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        loggedInAs = (User)args.getSerializable(LOGGED_IN_AS_KEY);
        System.out.println(loggedInAs + " is person logged in !");

        viewing = (User) args.getSerializable(FeedFragment.TO_VIEW_KEY);

        firstLastName = findViewById(R.id.person_firstLastName);
        alias = findViewById(R.id.person_alias);
        numFollowers = findViewById(R.id.person_followersCount);
        numPeopleFollowing = findViewById(R.id.person_followingCount);
        profilePicture = findViewById(R.id.person_profilePicture);
        profilePicture.setImageDrawable(ImageUtils.drawableFromByteArray(viewing.getImageBytes()));
        followButton = findViewById(R.id.person_followingButton);


        firstLastName.setText(viewing.getFirstName() + " " + viewing.getLastName());
        alias.setText("@" + viewing.getUserName());
        UserStatsRequest req = new UserStatsRequest(viewing,AuthTokenHolder.authToken,loggedInAs);
        UserStatsTask task = new UserStatsTask(new UserStatsPresenter(),this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);

        tabs = findViewById(R.id.person_tabs);
        pager = findViewById(R.id.person_viewPager);
        PersonPagerAdapter adapter = new PersonPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        adapter.setUser(viewing,loggedInAs);
        tabs.setupWithViewPager(pager);

        FollowingStatusTask theTask = new FollowingStatusTask(this,new FollowingStatusPresenter());
        FollowingStatusRequest request = new FollowingStatusRequest(loggedInAs,viewing);
        theTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,request);



    }

    @Override
    public void updateValues(UserStatsResponse resp) {
        numFollowers.setText( Integer.toString(resp.getNumFollowers()) );
        numPeopleFollowing.setText( Integer.toString(resp.getNumPeopleTheyAreFollowing()) );
    }

    @Override
    public void update(FollowingStatusResponse resp) {

        if(resp.isFollows()){
            followButton.setText("Following");
            followButton.setOnClickListener((c) -> {
                FollowManipulationTask task = new FollowManipulationTask(new FollowManipulationPresenter(),this);
                FollowManipulationRequest req = new FollowManipulationRequest(loggedInAs,viewing,false, AuthTokenHolder.authToken);
                task.execute(req);
            });
        } else {
            followButton.setText("Not Following");
            followButton.setOnClickListener((c) -> {
                FollowManipulationTask task = new FollowManipulationTask(new FollowManipulationPresenter(),this);
                FollowManipulationRequest req = new FollowManipulationRequest(loggedInAs,viewing,true,AuthTokenHolder.authToken);
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);
            });

        }
    }

    @Override
    public void setResults(FollowManipulationResult res) {
        System.out.println("we are currently following"+ res.isNowFollowing());
        System.out.println("We are setting results!");
        update(new FollowingStatusResponse(res.isNowFollowing()));
    }

    @Override
    public void modelUpdated() {
        UserStatsRequest req = new UserStatsRequest(viewing,AuthTokenHolder.authToken,loggedInAs);
        UserStatsTask task = new UserStatsTask(new UserStatsPresenter(),this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);
    }
}