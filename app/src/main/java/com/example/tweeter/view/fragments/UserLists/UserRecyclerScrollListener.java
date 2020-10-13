package com.example.tweeter.view.fragments.UserLists;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tweeter.view.fragments.PaginatedFragment;
import com.example.tweeter.view.fragments.UserLists.UserRecyclerViewAdapter;

public class UserRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private UserRecyclerViewAdapter recyclerViewAdapter;
    private PaginatedFragment fragment;

    public UserRecyclerScrollListener(LinearLayoutManager layoutManager, UserRecyclerViewAdapter recyclerViewAdapter, PaginatedFragment fragment) {
        super();
        this.layoutManager = layoutManager;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.fragment = fragment;


    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if(!fragment.isLoading() && fragment.hasMore()){
            //maybe load more
            if ((visibleItemCount + firstVisibleItemPosition) >=
                    totalItemCount && firstVisibleItemPosition >= 0){
                fragment.loadMore();
            }
        }


    }
}