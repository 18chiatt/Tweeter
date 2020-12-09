package com.example.tweeter.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweeter.R;
import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.UserStatsRequest;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.presenter.UserStatsPresenter;
import com.example.tweeter.services.AuthTokenHolder;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.Tasks.UserStatsTask;
import com.example.tweeter.view.util.ImageUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements UserStatsTask.Observer, ModelObserver {
    public static final String USER_KEY = "USER_KEYYY";
    Button logoutButton;
    ImageView profileImage;
    LoginResponse response;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView numFollowers;
    TextView numPeopleTheyAreFollowing;
    TextView firstNameLastName;
    TextView alias;
    User loggedInAs;
    FloatingActionButton tweetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegisterObserverTask task4 = new RegisterObserverTask(new ObserverNotificationPresenter());
        task4.execute(this);
        setContentView(R.layout.activity_main);
        response = (LoginResponse) getIntent().getExtras().get(LoginResponse.LOGIN_RESPONSE_KEY);

        loggedInAs = response.getLoggedInAs();

        if(loggedInAs == null){
            loggedInAs = (User) getIntent().getExtras().getSerializable(USER_KEY);
        }


        System.out.println(loggedInAs.getFirstName());
        logoutButton = (Button) findViewById(R.id.logout_button);

        logoutButton.setOnClickListener((c)-> {
            finish();
        });
        profileImage = (ImageView) findViewById(R.id.profile_picture);
        profileImage.setImageDrawable(ImageUtils.drawableFromByteArray(loggedInAs.getImageBytes()));
        tabLayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.viewPager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.setUser(loggedInAs);
        tabLayout.setupWithViewPager(viewPager);

        numFollowers = findViewById(R.id.numFollowers);
        numPeopleTheyAreFollowing = findViewById(R.id.numPeopleFollowingThem);


        firstNameLastName = findViewById(R.id.firstNameLastName);
        alias = findViewById(R.id.alias);
        firstNameLastName.setText(loggedInAs.getFirstName() + " " + loggedInAs.getLastName());
        alias.setText("@"+loggedInAs.getUserName());
        tweetButton = findViewById(R.id.tweetActivityLauncher);

        UserStatsTask task = new UserStatsTask(new UserStatsPresenter(),this);
        UserStatsRequest req = new UserStatsRequest(loggedInAs, AuthTokenHolder.authToken,loggedInAs);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);

        tweetButton.setOnClickListener((c)-> {
            Intent intent = new Intent(this, TweetActivity.class);
            Bundle args = new Bundle();
            args.putSerializable(TweetActivity.USER_KEY,loggedInAs);
            intent.putExtras(args);
            startActivity(intent);

        });


    }


    @Override
    public void updateValues(UserStatsResponse resp) {
        numFollowers.setText(Integer.toString( resp.getNumFollowers()));
        numPeopleTheyAreFollowing.setText( Integer.toString(resp.getNumPeopleTheyAreFollowing()));

    }

    @Override
    public void modelUpdated() {
        UserStatsTask task = new UserStatsTask(new UserStatsPresenter(),this);
        UserStatsRequest req = new UserStatsRequest(loggedInAs,AuthTokenHolder.authToken,loggedInAs);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);
    }
}