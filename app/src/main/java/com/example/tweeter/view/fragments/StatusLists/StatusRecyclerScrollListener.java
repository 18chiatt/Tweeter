package com.example.tweeter.view.fragments.StatusLists;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tweeter.view.fragments.PaginatedFragment;

public class StatusRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private StatusRecyclerViewAdapter adapter;
    private PaginatedFragment fragment;


    public StatusRecyclerScrollListener(LinearLayoutManager man, StatusRecyclerViewAdapter adapter, PaginatedFragment frag){
        this.layoutManager = man;
        this.adapter = adapter;
        this.fragment = frag;

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
