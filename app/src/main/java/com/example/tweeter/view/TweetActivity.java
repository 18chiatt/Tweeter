package com.example.tweeter.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tweeter.R;
import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.presenter.PostStatusPresenter;
import com.example.tweeter.services.AuthTokenHolder;
import com.example.tweeter.view.Tasks.PostStatusTask;
import com.example.tweeter.view.util.ImageUtils;

import java.time.Instant;

public class TweetActivity extends AppCompatActivity implements PostStatusTask.Observer {
    public static final String USER_KEY = "user_key";
    private TextView firstLastName;
    private TextView alias;
    private ImageView profilePic;
    private Button submit;
    private EditText entry;
    private User loggedInAs;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        loggedInAs = (User) getIntent().getExtras().getSerializable(USER_KEY);


        firstLastName = findViewById(R.id.tweet_firstLastName);
        alias = findViewById(R.id.tweet_alias);
        profilePic = findViewById(R.id.tweet_profilePic);
        submit = findViewById(R.id.tweet_submit);
        entry = findViewById(R.id.tweet_editText);
        cancel = findViewById(R.id.tweet_cancel);
        submit.setEnabled(false);

        cancel.setText("Cancel");
        firstLastName.setText(loggedInAs.getFirstName() + " " + loggedInAs.getLastName());
        alias.setText("@" + loggedInAs.getUserName());
        profilePic.setImageDrawable(ImageUtils.drawableFromByteArray(loggedInAs.getImageBytes()));
        entry.setText("");
        submit.setText("Submit");
        cancel.setOnClickListener((c) -> {
            finish();
        });

        entry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().equals("")){
                    submit.setEnabled(false);
                } else {
                    submit.setEnabled(true);
                }

            }
        });

        submit.setOnClickListener((c)-> {
            Status toPost = new Status(entry.getText().toString(), Instant.now().getEpochSecond(),loggedInAs);
            PostStatusRequest req = new PostStatusRequest(toPost, AuthTokenHolder.authToken);
            PostStatusTask task = new PostStatusTask(new PostStatusPresenter(),this);
            task.execute(req);
        });

    }

    @Override
    public void afterPost(PostStatusResponse resp) {
        this.finish();
    }
}