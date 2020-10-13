package com.example.tweeter.view.fragments.UserLists;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweeter.R;
import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.presenter.FollowersPresenter;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.view.PersonActivity;
import com.example.tweeter.view.Tasks.FollowersTask;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.fragments.PaginatedFragment;
import com.example.tweeter.view.fragments.StatusLists.FeedFragment;
import com.example.tweeter.view.fragments.StatusLists.IntentFulfiller;
import com.example.tweeter.view.fragments.UserLists.UserRecyclerScrollListener;
import com.example.tweeter.view.fragments.UserLists.UserRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FollowersFragment extends Fragment implements FollowersTask.Observer, PaginatedFragment, ModelObserver, IntentFulfiller {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static String USER_KEY = "UserKey";
    public static int USER_PER_PAGE = 6;
    private boolean hasMore = true;
    private boolean isLoading = false;
    User toGetFollowersOf;
    List<User> toDisplay;
    User previousLast;
    UserRecyclerViewAdapter adapter;




    public FollowersFragment() {
        // Required empty public constructor
        System.out.println("Shouldn't have called this! Use paramaterized constructor!");
    }

    public FollowersFragment(User toGetOf){
        RegisterObserverTask task6 = new RegisterObserverTask(new ObserverNotificationPresenter());
        task6.execute(this);
        toGetFollowersOf = toGetOf;
        previousLast = null;
        toDisplay = new ArrayList<>();
        FollowersTask task = new FollowersTask();
        task.setObserver(this,new FollowersPresenter());
        FollowerRequest req =  new FollowerRequest(toGetFollowersOf,USER_PER_PAGE,previousLast);
        this.isLoading = true;
        task.execute(req);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.followerRecyclerView);
        adapter = new UserRecyclerViewAdapter(toDisplay,getContext(),this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager man = new LinearLayoutManager(getContext());
        UserRecyclerScrollListener scroll = new UserRecyclerScrollListener(man,adapter,this);
        recyclerView.setOnScrollListener(scroll);
        recyclerView.setLayoutManager(man);


        return view;
    }

    @Override
    public void addFollowers(FollowerResponse r) {
        this.toDisplay.addAll(r.getFollowers());
        if(r.getFollowers().size() > 1) {
            previousLast = r.getFollowers().get(r.getFollowers().size() - 1);
        }
        adapter.updateData(this.toDisplay);
        this.hasMore = r.hasMore();
        this.isLoading = false;


    }

    @Override
    public boolean hasMore() {
        return this.hasMore;
    }

    @Override
    public boolean isLoading() {
        return this.isLoading;
    }

    @Override
    public void setLoading(boolean toSet) {
        this.isLoading = toSet;
    }

    @Override
    public void loadMore() {
        FollowersTask task = new FollowersTask();
        task.setObserver(this,new FollowersPresenter());
        FollowerRequest req =  new FollowerRequest(toGetFollowersOf,USER_PER_PAGE,previousLast);
        this.isLoading = true;
        System.out.println(req);
        task.execute(req);
    }


    @Override
    public void modelUpdated() {
        this.toDisplay.clear();
        adapter.clear();
        this.hasMore = true;
        this.isLoading = false;
        previousLast = null;
        loadMore();
    }

    @Override
    public void startPersonActivity(User userToView) {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(FeedFragment.LOGGED_IN_AS_KEY,toGetFollowersOf);
        args.putSerializable(FeedFragment.TO_VIEW_KEY,userToView);
        intent.putExtras(args);
        startActivity(intent);
    }
}