package com.example.tweeter.view.fragments.StatusLists;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.view.PersonActivity;
import com.example.tweeter.R;
import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.presenter.FeedPresenter;
import com.example.tweeter.view.Tasks.FeedTask;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.fragments.PaginatedFragment;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment implements PaginatedFragment, FeedTask.Observer, IntentFulfiller , ModelObserver {
    private User theUser;
    private User loggedInAs;
    private Status previousLast;
    private boolean isLoading;
    private boolean hasMore;
    private static final int numStatusPerPage = 6;
    public static final String LOGGED_IN_AS_KEY = "LOGGED_IN_AS";
    public static final String TO_VIEW_KEY = "ToView";
    List<Status> toDisplay;
    RecyclerView recyclerView;
    private StatusRecyclerViewAdapter adapter;
    boolean ignoreNext = false;


    public FeedFragment() {
        // Required empty public constructor
        System.out.println("invalid constructor! Use paramaterized");
        String crasher = null;
        System.out.println(crasher.length());
    }

    public FeedFragment(User theUser, User loggedInAs){
        this.toDisplay = new ArrayList<>();
        RegisterObserverTask task6 = new RegisterObserverTask(new ObserverNotificationPresenter());
        this.loggedInAs = loggedInAs;
        task6.execute(this);
        this.theUser = theUser;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView = view.findViewById(R.id.feed_recycler);
        adapter = new StatusRecyclerViewAdapter(toDisplay,getContext(),this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager man = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(man);
        StatusRecyclerScrollListener listener = new StatusRecyclerScrollListener(man,adapter,this);
        recyclerView.setOnScrollListener(listener);
        loadMore();

        return view;
    }

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void setLoading(boolean toSet) {
        this.isLoading = toSet;
    }

    @Override
    public void loadMore() {
        this.isLoading = true;
        FeedRequest request = new FeedRequest(theUser,numStatusPerPage,previousLast);
        FeedTask task = new FeedTask(this,new FeedPresenter());
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,request);

    }


    @Override
    public void updateModel(FeedResponse resp) {
        if(this.ignoreNext){
            this.ignoreNext = false;
            return;
        }
        //this.toDisplay.addAll(resp.getTheStatus());
        if(resp.getTheStatus().size() > 0) {
            this.previousLast = resp.getTheStatus().get(resp.getTheStatus().size() - 1);
            hasMore = resp.isHasMore();
        }
        else {
            this.hasMore = false;
        }

        adapter.updateStuff(resp.getTheStatus());
        adapter.notifyDataSetChanged();
        isLoading = false;
    }

    @Override
    public void startPersonActivity(User userToView) {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        Bundle args = new Bundle();

        args.putSerializable(LOGGED_IN_AS_KEY,loggedInAs);
        args.putSerializable(TO_VIEW_KEY,userToView);
        intent.putExtras(args);
        startActivity(intent);
    }



    @Override
    public void modelUpdated() {
        adapter.clear();
        this.hasMore = true;
        if(this.isLoading){
            this.ignoreNext = true;
        }
        this.isLoading = false;
        previousLast = null;
        System.out.println("modelUPDATED");


        loadMore();
    }
}