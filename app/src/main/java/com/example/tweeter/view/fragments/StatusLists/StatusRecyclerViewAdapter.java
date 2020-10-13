package com.example.tweeter.view.fragments.StatusLists;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tweeter.R;
import com.example.tweeter.Server.ModelObserver;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusRecyclerViewAdapter extends RecyclerView.Adapter<StatusRecyclerViewAdapter.ViewHolder>  {
    public StatusRecyclerViewAdapter(List<Status> toShow, Context context, IntentFulfiller fulfiller) {
        //register self with Server

        this.fulfiller = fulfiller;
        if(toShow != null){
            this.toShow = toShow;
        } else {
            this.toShow = new ArrayList<>();
        }


        this.context = context;
    }

    public void updateStuff(List<Status> toAdd){
        toShow.addAll(toAdd);
        this.notifyDataSetChanged();
    }

    List<Status> toShow;
    Context context;
    private IntentFulfiller fulfiller;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    public void clear(){
        this.toShow.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Status toBind = toShow.get(position);
        User saidBy = toBind.getSaidBy();
        holder.profilePic .setImageDrawable(ImageUtils.drawableFromByteArray(saidBy.getImageBytes()));
        holder.firstLastname.setText(saidBy.getFirstName() + " " + saidBy.getLastName());
        holder.alias.setText(saidBy.getUserName());
        holder.layout.setOnClickListener((c)-> {
            fulfiller.startPersonActivity(saidBy);
        });
        SpannableString ss = new SpannableString(toBind.getMessage());
        ss= getUsernameSpans(toBind.getMessage(),ss,context);
        ss = setLinkSpans(toBind.getMessage(),ss,context);
        holder.tweetMessage.setMovementMethod(LinkMovementMethod.getInstance());
        holder.tweetMessage.setText(ss);
        holder.dateTime.setText( toBind.getTimeOfPost().toString());
    }

    @Override
    public int getItemCount() {
        return toShow.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstLastname;
        public TextView alias;
        public TextView dateTime;
        public TextView tweetMessage;
        public ImageView profilePic;
        public ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstLastname = itemView.findViewById(R.id.status_list_firstNameLastName);
            alias = itemView.findViewById(R.id.status_list_alias);
            dateTime = itemView.findViewById(R.id.status_list_dateTime);
            tweetMessage = itemView.findViewById(R.id.status_list_message);
            profilePic = itemView.findViewById(R.id.status_list_image);
            layout = itemView.findViewById(R.id.status_list_container);
        }
    }

    private SpannableString getUsernameSpans(String message, SpannableString toAddTo, Context context){
        String patternString = "@[a-zA-Z0-9-_]*";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(message);

        while(matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            UsernameSpan span = new UsernameSpan(message.substring(start+1,end),context,fulfiller);

            toAddTo.setSpan(span,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return toAddTo;


    }

    private SpannableString setLinkSpans(String message, SpannableString toAddTo, Context context){
        String patternString = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(message);

        while(matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            System.out.println(message.substring(start,end));
            LinkSpan span = new LinkSpan(fulfiller,message.substring(start,end));
            toAddTo.setSpan(span,start,end,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return toAddTo;
    }
}
