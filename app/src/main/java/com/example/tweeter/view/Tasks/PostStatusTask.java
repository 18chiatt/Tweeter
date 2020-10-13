package com.example.tweeter.view.Tasks;

import android.os.AsyncTask;

import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.presenter.PostStatusPresenter;

public class PostStatusTask extends AsyncTask<PostStatusRequest,Integer,Integer> {
    private PostStatusPresenter toUse;
    private PostStatusResponse toReturn;
    private Observer toCall;

    public PostStatusTask(PostStatusPresenter toUse, Observer toCall) {
        this.toUse = toUse;
        this.toCall = toCall;
    }

    public interface Observer {
        public void afterPost(PostStatusResponse resp);
    }

    @Override
    protected Integer doInBackground(PostStatusRequest... postStatusRequests) {
        this.toReturn = toUse.postStatus(postStatusRequests[0]);
        return 3;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        for(ModelObserver o : toReturn.getToUpdate()){
            o.modelUpdated();
        }

        toCall.afterPost(toReturn);
    }
}
