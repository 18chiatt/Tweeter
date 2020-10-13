package com.example.tweeter.view.fragments.UserLists;

import android.content.Context;
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
import com.example.tweeter.model.domain.User;
import com.example.tweeter.presenter.ObserverNotificationPresenter;
import com.example.tweeter.view.Tasks.RegisterObserverTask;
import com.example.tweeter.view.fragments.StatusLists.IntentFulfiller;
import com.example.tweeter.view.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>  {
    public UserRecyclerViewAdapter(List<User> usersToDisplay, Context context, IntentFulfiller fulfiller) {
        this.fulfiller = fulfiller;
        this.usersToDisplay = new ArrayList<>();
        if(usersToDisplay != null)
            this.usersToDisplay.addAll(usersToDisplay);

        this.context = context;
    }

    public void clear(){
        this.usersToDisplay.clear();
        this.notifyDataSetChanged();
    }

    public void updateData(List<User> toDisplay){
        this.usersToDisplay = toDisplay;
        System.out.println(toDisplay.size());
        notifyDataSetChanged();
    }

    private List<User> usersToDisplay;
    private Context context;
    private IntentFulfiller fulfiller;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(usersToDisplay.get(holder.getAdapterPosition()).getImageBytes() == null){
            System.out.println("Haven't stored image correctly!!");
        }
        holder.profilePic.setImageDrawable(ImageUtils.drawableFromByteArray(usersToDisplay.get(holder.getAdapterPosition()).getImageBytes()));
        holder.firstNameLastname.setText(usersToDisplay.get(holder.getAdapterPosition()).getFirstName() + " " + usersToDisplay.get(holder.getAdapterPosition()).getLastName());
        holder.userName.setText(usersToDisplay.get(holder.getAdapterPosition()).getUserName());
        holder.box.setOnClickListener((c)-> {
            fulfiller.startPersonActivity(usersToDisplay.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return usersToDisplay.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePic;
        public TextView firstNameLastname;
        public TextView userName;
        public ConstraintLayout box;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.list_profile_picture);
            firstNameLastname = itemView.findViewById(R.id.list_first_last_name);
            userName = itemView.findViewById(R.id.list_user_name);
            box = itemView.findViewById(R.id.list_item_holder);

        }
    }




}
