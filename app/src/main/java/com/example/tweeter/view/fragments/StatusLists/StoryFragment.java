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
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.presenter.StoryPresenter;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.Tasks.StoryTask;
import com.example.tweeter.view.fragments.PaginatedFragment;

import java.util.List;
import java.util.concurrent.Executor;


public class StoryFragment extends Fragment implements StoryTask.Observer, IntentFulfiller , PaginatedFragment, ModelObserver {
    private User loggedInAs;
    private User theUser;
    private Status previousLast;
    private boolean isLoading;
    private boolean hasMore;
    private static final int numStatusPerPage = 6;
    List<Status> toDisplay;
    RecyclerView recyclerView;
    private StatusRecyclerViewAdapter adapter;

    public StoryFragment() {
        // Required empty public constructor
        System.out.println("Used wrong constructor, noob");
    }

    public StoryFragment(User u, User loggedInAs){
        RegisterObserverTask task6 = new RegisterObserverTask(new ObserverNotificationPresenter());
        task6.execute(this);
        this.loggedInAs = loggedInAs;
        this.theUser = u;
        previousLast = null;
        isLoading = false;
        hasMore = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        recyclerView = view.findViewById(R.id.story_recycler);
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

    public void loadMore(){
        isLoading = true;
        StoryRequest req = new StoryRequest(numStatusPerPage,theUser,previousLast);

        StoryTask task = new StoryTask(new StoryPresenter(),this);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,req);



    }

    @Override
    public void updateModel(StoryResponse resp) {
        if(resp.getTheStatus().size() == 0){
            this.hasMore = false;

            return;
        }
        this.previousLast = resp.getTheStatus().get(resp.getTheStatus().size()-1);
        hasMore = resp.isHasMore();
        adapter.updateStuff(resp.getTheStatus());
        adapter.notifyDataSetChanged();
        isLoading = false;


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

    @Override
    public void modelUpdated() {
        adapter.clear();
        this.hasMore = true;
        this.isLoading = false;
        previousLast = null;
        loadMore();
    }


}