package com.example.tweeter.Server;

import android.hardware.camera2.CameraManager;
import android.os.Build;

import com.example.tweeter.Server.generators.FollowGenerator;
import com.example.tweeter.Server.generators.TweetGenerator;
import com.example.tweeter.Server.generators.UserGenerator;
import com.example.tweeter.model.Response.FeedResponse;
import com.example.tweeter.model.Response.FollowManipulationResult;
import com.example.tweeter.model.Response.FollowerResponse;
import com.example.tweeter.model.Response.FollowingResponse;
import com.example.tweeter.model.Response.FollowingStatusResponse;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.Response.PostStatusResponse;
import com.example.tweeter.model.Response.RegisterResponse;
import com.example.tweeter.model.Response.StoryResponse;
import com.example.tweeter.model.Response.UserResponse;
import com.example.tweeter.model.Response.UserStatsResponse;
import com.example.tweeter.model.domain.Follow;
import com.example.tweeter.model.domain.Status;
import com.example.tweeter.model.domain.User;
import com.example.tweeter.model.request.FeedRequest;
import com.example.tweeter.model.request.FollowManipulationRequest;
import com.example.tweeter.model.request.FollowerRequest;
import com.example.tweeter.model.request.FollowingRequest;
import com.example.tweeter.model.request.FollowingStatusRequest;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.model.request.PostStatusRequest;
import com.example.tweeter.model.request.RegisterRequest;
import com.example.tweeter.model.request.StoryRequest;
import com.example.tweeter.model.request.UserRequest;
import com.example.tweeter.model.request.UserStatsRequest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ServerFake implements ServerFacade {
    private final int numPeopleToGenerate = 200;
    private final int numPeopleEveryoneFollows = 20;
    private final int numTweets = 10;
    private static SortedSet<Status> theTweets;
    private TweetGenerator gen;
    private static List<User> theUsers;
    private static List<Follow> theFollows;
    private static Follow toRemove;
    private static List<ModelObserver> toNotify;





    public ServerFake() {
        if(theUsers == null) {
            theUsers = new ArrayList<>(numPeopleToGenerate + 1);
            for (int i = 0; i< numPeopleToGenerate; i++){
                theUsers.add(UserGenerator.getUser());
            }
        }

        if(theFollows == null){
            theFollows = new ArrayList<>();
        }

        if(toNotify == null){
            toNotify= new ArrayList<>();
        }
        if(toRemove != null){
            theFollows.remove(toRemove);
            toRemove = null;
        }


    }

    public void generateTweets(){
        gen = new TweetGenerator(theUsers,numTweets);

        theTweets = new TreeSet<>();
        for(User u : theUsers){
            theTweets.addAll(gen.getTweets(u));
        }

    }


    private void generateFollows () {

        theFollows = new ArrayList<>(numPeopleToGenerate+1 * numPeopleEveryoneFollows);
        FollowGenerator theGenerator = new FollowGenerator(theUsers);

        for(User u : theUsers){
            theFollows.addAll(theGenerator.generateFollows(u,numPeopleEveryoneFollows));
        }
        if(theFollows == null){
            System.out.println("It's null!");
        }
        return;
    }


    @Override
    public FollowingResponse getFollowing(FollowingRequest req) {

        User personWhoFollows = req.getPersonWhoFollows();
        int max = req.getLimit();
        return getUsersBeingFollowed(personWhoFollows,max,req.getLastOneGotten());



    }

    @Override
    public FollowerResponse getFollowers(FollowerRequest req) {
        User toFindOf = req.getWhoTheyFollow();
        int max = req.getMaxToGet();
        FollowerResponse resp = getThoseFollowing(toFindOf,req.getMaxToGet(),req.getPreviousLast());


        return resp;

    }





    @Override
    public LoginResponse login(LoginRequest req) {

        User res = null;
        System.out.println(req.getUserName());
        System.out.println(theUsers.size());
        for(User u : theUsers){
            if(u.getUserName().equals(req.getUserName())) {
                res = u;
                break;
            }

        }

        if(res == null){
            return new LoginResponse(null,null,false);
        }
        if(theFollows == null || theFollows.size() <300){
            generateFollows();
        }

        if(theTweets == null || theTweets.size() < 300){
            generateTweets();
        }

        return new LoginResponse(res,"1234",true);
    }

    @Override
    public UserStatsResponse getUserStats(UserStatsRequest req) {
        int followers = 0;
        int peopleFollowing = 0;
        User toFindOf = req.getToFindOf();

        for(Follow follow : theFollows){

            if(follow.getFollower().equals(toFindOf)){
                peopleFollowing++;
            }
            if(follow.getPersonBeingFollowed().equals(toFindOf)){
                followers++;
            }

        }
        return new UserStatsResponse(followers,peopleFollowing);

    }

    @Override
    public StoryResponse getStory(StoryRequest req) {
        List<Status> toReturn = new ArrayList<>();
        Iterator<Status> iter = theTweets.iterator();

        if(req.getPreviousLast() != null){
            while(iter.hasNext() ){
                if(iter.next().equals(req.getPreviousLast())){
                    break;
                }
            }

        }

        int remaining = req.getMaxToGet();
        while(iter.hasNext() && remaining > 0){
            Status next = iter.next();
            if(next.getSaidBy().equals(req.getToGetOf())){
                remaining--;
                toReturn.add(next);
            }
        }

        StoryResponse actualToReturn = new StoryResponse(toReturn,remaining==0);

        return actualToReturn;
    }





    private FollowerResponse getThoseFollowing(User toFindOf, int maxToGet, User lastToNotInclude) {
        int index =0;
        boolean hasMore;

        Deque<User> toReturn = new ArrayDeque<>();
        if(lastToNotInclude != null){
            while(!theFollows.get(index).getFollower().equals(lastToNotInclude) || theFollows.get(index).getPersonBeingFollowed().equals(toFindOf) == false){ //while follow[index] is NOT lastToNotInclude, iterate
                index++;
            }
            //index is lastToNotInclude
            index++; //index is now starting index
        }
        index++;

        int remaining = maxToGet;
        for(int i = index; i < theFollows.size() && remaining > 0; i ++){
            if(theFollows.get(i).getPersonBeingFollowed().equals(toFindOf)){
                toReturn.add(theFollows.get(i).getFollower());
                remaining--;
            }
        }


        hasMore = remaining == 0;
        if(toReturn.size() != 0) {
            if (lastToNotInclude != null && toReturn.getFirst().equals(lastToNotInclude)) {
                toReturn.pop();
            }
        }
        List<User> listToReturn = new ArrayList<>();
        listToReturn.addAll(toReturn);
        return new FollowerResponse(listToReturn, hasMore);






    }

    private FollowingResponse getUsersBeingFollowed(User toFindOf,int maxToGet, User lastToNotInclude){

        int index = 0;
        List<User> toReturn = new ArrayList<>();
        if(lastToNotInclude != null){

            while(theFollows.get(index).getPersonBeingFollowed().equals(lastToNotInclude) == false || theFollows.get(index).getFollower().equals(toFindOf) == false){ //while follow[index] is NOT lastToNotInclude, iterate
                index++;
            }
            //index is lastToNotInclude
            index++; //index is now starting index
        }


        int remaining = maxToGet;
        for(int i = index; i < theFollows.size() && remaining > 0; i++){
            if(theFollows.get(i).getFollower().equals(toFindOf)){

                toReturn.add(theFollows.get(i).getPersonBeingFollowed());
                remaining--;
            }
        }

        boolean hasMore = false;
        if(remaining == 0){
            hasMore = true;
        }

        FollowingResponse resp = new FollowingResponse(toReturn,hasMore);


        return resp;

    }

    @Override
    public UserResponse getUser(UserRequest req) {

        for(User u : theUsers){
            if(u.getUserName().equals(req.getAlias())){

                return new UserResponse(u,true);
            }
        }
        System.out.println("Unable to find correct user!");
        return new UserResponse(null,false);
    }

    @Override
    public FeedResponse getFeed(FeedRequest req) {
        User toGetOf = req.getToGetFeedOf();
        Set<User> peopleTheyFollow = new HashSet<>();
        peopleTheyFollow.addAll( getUsersBeingFollowed(toGetOf,Integer.MAX_VALUE,null).getUsersTheyAreFollowing());
        List<Status> toReturn = new ArrayList<>();

        Iterator<Status> iter = theTweets.iterator();

        if(req.getPreviousLast() != null){
            while(iter.hasNext()){
                Status theStatus = iter.next();
                if(theStatus.equals(req.getPreviousLast())){
                    iter.next();
                    break;
                }
            }
        }

        int remaining = req.getMaxToGet();
        while(iter.hasNext() && remaining > 0){
            Status next = iter.next();
            if(peopleTheyFollow.contains(next.getSaidBy())){
                remaining--;
                toReturn.add(next);
            }

        }
        System.out.println("Size is " + toReturn.size());
        FeedResponse response = new FeedResponse(toReturn,remaining==0);

        return response;

    }

    @Override
    public FollowingStatusResponse getFollowingStatus(FollowingStatusRequest req) {
        for(Follow f : theFollows){
            if(f.getPersonBeingFollowed().equals(req.getPersonWhoIsFollowedMaybe()) && f.getFollower().equals(req.getPersonWhoFollowsMaybe())){
                return new FollowingStatusResponse(true);
            }
        }
        return new FollowingStatusResponse(false);
    }

    @Override
    public FollowManipulationResult manipulateFollow(FollowManipulationRequest req) {
        if(req.isAddFollow()){
            if(req.getPersonWhoFollows().equals(req.getPersonWhoIsFollowed())){
                return new FollowManipulationResult(false,toNotify);
            }
            theFollows.add(new Follow(req.getPersonWhoFollows(),req.getPersonWhoIsFollowed()));

            return new FollowManipulationResult(true,toNotify);
        } else {
            for(Follow f : theFollows){
                if(f.getFollower().equals(req.getPersonWhoFollows()) && f.getPersonBeingFollowed().equals(req.getPersonWhoIsFollowed())){
                    toRemove = f;
                }
            }

            return new FollowManipulationResult(false,toNotify);
        }
    }

    @Override
    public PostStatusResponse postStatus(PostStatusRequest req) {
        theTweets.add(req.getTheStatus());
        return new PostStatusResponse(toNotify);
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest req) {
        User newUser = new User(req.getFirstName(),req.getLastName(),req.getUsername(),"https://pbs.twimg.com/profile_images/1305883698471018496/_4BfrCaP_400x400.jpg");
        theUsers.add(newUser);

        if(theUsers.size() > 1){
            FollowGenerator theGenerator = new FollowGenerator(theUsers);
            theFollows.addAll(theGenerator.generateFollows(newUser,numPeopleEveryoneFollows));
            theFollows.addAll(theGenerator.makePeopleFollow(newUser, numPeopleEveryoneFollows)) ;

        }

        if(theTweets != null && theTweets.size() > 1){
            gen = new TweetGenerator(theUsers,numTweets);
            theTweets.addAll(gen.getTweets(newUser));
        }

        return new RegisterResponse(true);
    }

    @Override
    public void registerObserver(ModelObserver obs) {
        toNotify.add(obs);
    }
}
