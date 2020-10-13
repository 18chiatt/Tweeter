package com.example.tweeter.view.fragments;

public interface PaginatedFragment {
    boolean hasMore();
    boolean isLoading();
    void setLoading(boolean toSet);
    void loadMore();

}
