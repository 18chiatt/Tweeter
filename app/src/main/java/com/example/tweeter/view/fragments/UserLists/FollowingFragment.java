package com.example.tweeter.view.fragments.UserLists;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweeter.R;
import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.presenter.FollowingPresenter;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.view.PersonActivity;
import com.example.tweeter.view.Tasks.FollowingTask;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.fragments.PaginatedFragment;
import com.example.tweeter.view.fragments.StatusLists.FeedFragment;
import com.example.tweeter.view.fragments.StatusLists.IntentFulfiller;
import com.example.tweeter.view.fragments.UserLists.UserRecyclerScrollListener;
import com.example.tweeter.view.fragments.UserLists.UserRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FollowingFragment extends Fragment implements PaginatedFragment, FollowingTask.Observer, ModelObserver, IntentFulfiller {
    User toGetOf;
    User previousLast;
    FollowingPresenter presenter;
    public static int USER_PER_PAGE = 6;
    private boolean hasMore = true;
    private boolean isLoading = false;
    private User loggedInAs;
    private List<User> toDisplay;
    UserRecyclerViewAdapter adapter;




    public FollowingFragment() {
        // Required empty public constructor
        System.out.println("Shouldn't have called this! Use paramaterized constructor!");
    }

    public FollowingFragment(User u, User loggedInAs){
        RegisterObserverTask task6 = new RegisterObserverTask(new ObserverNotificationPresenter());
        task6.execute(this);
        this.loggedInAs = loggedInAs;
        toDisplay = new ArrayList<>();
        toGetOf = u;
        previousLast = null;
        presenter = new FollowingPresenter();
        FollowingTask task = new FollowingTask(this,presenter);
        FollowingRequest req = new FollowingRequest(toGetOf,USER_PER_PAGE,previousLast);
        this.isLoading = true;
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.followingRecyclerView);
        adapter = new UserRecyclerViewAdapter(toDisplay,getContext(), this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager man = new LinearLayoutManager(getContext());
        UserRecyclerScrollListener scroll = new UserRecyclerScrollListener(man,adapter,this);
        recyclerView.setOnScrollListener(scroll);
        recyclerView.setLayoutManager(man);

        return view;
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
        this.isLoading = true;
        FollowingTask task = new FollowingTask(this,presenter);
        FollowingRequest req = new FollowingRequest(toGetOf,USER_PER_PAGE,previousLast);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);

    }

    @Override
    public void addFollowing(FollowingResponse res) {
        toDisplay.addAll(res.getUsersTheyAreFollowing());
        if(res.getUsersTheyAreFollowing().size() == 0){
            this.hasMore =res.hasMore();
            this.isLoading = false;
            return;
        }

        previousLast = res.getUsersTheyAreFollowing().get(res.getUsersTheyAreFollowing().size()-1);
        System.out.println(toDisplay.size());
        adapter.updateData(toDisplay);
        this.hasMore = res.hasMore();
        this.isLoading = false;


    }

    @Override
    public void modelUpdated() {
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
        args.putSerializable(FeedFragment.LOGGED_IN_AS_KEY,loggedInAs);
        args.putSerializable(FeedFragment.TO_VIEW_KEY,userToView);
        intent.putExtras(args);
        startActivity(intent);
    }
}