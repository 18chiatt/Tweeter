package com.example.tweeter.model.Response;

import com.example.tweeter.model.domain.Status;

import java.util.List;

public class StoryResponse {
    public StoryResponse(List<Status> theStatus, boolean hasMore) {
        this.theStatus = theStatus;
        this.hasMore = hasMore;
    }

    public List<Status> getTheStatus() {
        return theStatus;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    private List<Status> theStatus;
    private boolean hasMore;



}
