package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.presenter.StoryPresenter;

public class StoryTask extends AsyncTask<StoryRequest,Integer,Integer> {
    private StoryResponse toRespondWith;
    private StoryPresenter toUse;
    private Observer toCall;

    public interface Observer {
        void updateModel(StoryResponse resp);
    }

    public StoryTask(StoryPresenter toUse, Observer toCall){
        this.toUse = toUse;
        this.toCall = toCall;
    }


    @Override
    protected Integer doInBackground(StoryRequest... storyRequests) {

        this.toRespondWith = toUse.getStory(storyRequests[0]);

        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        toCall.updateModel(toRespondWith);

    }
}
