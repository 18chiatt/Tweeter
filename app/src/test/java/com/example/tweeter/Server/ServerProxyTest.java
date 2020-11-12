package com.example.tweeter.Server;

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
import com.example.tweeter.services.FeedService;
import com.example.tweeter.services.FollowManipulationService;
import com.example.tweeter.services.FollowerService;
import com.example.tweeter.services.FollowingService;
import com.example.tweeter.services.FollowsStatusService;
import com.example.tweeter.services.ObserverNotificationService;
import com.example.tweeter.services.PostTweetService;
import com.example.tweeter.services.RegisterService;
import com.example.tweeter.services.StoryService;
import com.example.tweeter.services.UserStatsService;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServerProxyTest {
    //these are the integration tests
    ServerProxy proxy = new ServerProxy();
    User myUser = new User("Chase","Hiatt","username","https://pbs.twimg.com/profile_images/1305883698471018496/_4BfrCaP_400x400.jpg");
    User randomUser = new User("asdf","asdf","noob","https://pbs.twimg.com/profile_images/1305883698471018496/_4BfrCaP_400x400.jpg");
    User someoneTheyFollow = new User("Kylian","CRUZ","KylianCRUZ-812", "https://pbs.twimg.com/profile_images/1305883698471018496/_4BfrCaP_400x400.jpg");
    User somoneWhoFollowsMe = new User("Khari","BENJAMIN","KhariBENJAMIN566","https://pbs.twimg.com/profile_images/1305883698471018496/_4BfrCaP_400x400.jpg");


    @Test
    public void getFollowing() {
        FollowingRequest req = new FollowingRequest(myUser,6,null);
        FollowingResponse resp = new FollowingService().getFollowing(req);
        assert(resp.getUsersTheyAreFollowing().contains(someoneTheyFollow));
        assert(resp.getUsersTheyAreFollowing().size() == 5);

        req = new FollowingRequest(somoneWhoFollowsMe,20,null);
        resp = new FollowingService().getFollowing(req);
        assert(resp.getUsersTheyAreFollowing().contains(myUser));

        req = new FollowingRequest(randomUser,20,null);
        resp = new FollowingService().getFollowing(req);
        assert(resp.getUsersTheyAreFollowing().size() == 0);


    }

    @Test
    public void getFollowers() {
        FollowerRequest req = new FollowerRequest(myUser,6,null);
        FollowerResponse resp = new FollowerService().getFollowers(req);

        assert(resp.getFollowers().size() == 5);
        for(User u : resp.getFollowers()){
            assert(u.equals(myUser) == false);
        }
        assert(resp.getFollowers().contains(someoneTheyFollow));

        req = new FollowerRequest(someoneTheyFollow,20,null);
        resp = new FollowerService().getFollowers(req);
        assert(resp.getFollowers().contains(myUser));


    }

    @Test
    public void login() {
        LoginRequest loginRequest = new LoginRequest("username","password");
        LoginResponse resp = proxy.login(loginRequest);
        assert(resp.getLoggedInAs().equals(myUser));
        assert(resp.isSuccess());
        assert(resp.getAuthToken() != null);
    }

    @Test
    public void getUserStats() {
        UserStatsRequest req = new UserStatsRequest(myUser);
        UserStatsResponse resp = new UserStatsService().getUserStats(req);
        assert(resp.getNumPeopleTheyAreFollowing() == 5);
        assert(resp.getNumFollowers() == 5);
        //in this iteration of the fakeData, I have 5 following and followers
    }

    @Test
    public void getStory() {
        StoryRequest req = new StoryRequest(5,myUser,null);
        StoryResponse resp = new StoryService().getStory(req);
        assert(resp.getTheStatus().size() == 5);
        for (Status s : resp.getTheStatus()){
            assert(s.getSaidBy().equals(myUser));
        }
    }

    @Test
    public void getUser() {
        UserRequest req = new UserRequest("username");
        UserResponse response = proxy.getUser(req);
        assert(response.isSuccess());
        assert(response.getToRespondWith().getUserName().equals("username"));

    }

    @Test
    public void getFeed() {
        FeedRequest req = new FeedRequest(myUser,5,null);
        FeedResponse resp = new FeedService().getFeed(req);
        List<Status> theStatus = resp.getTheStatus();
        assert(theStatus.size() > 0);
        assert(resp.isHasMore());
        for(Status s : theStatus){
            assert(s.getSaidBy().equals(myUser) == false);
        }
    }

    @Test
    public void getFollowingStatus() {
        FollowingStatusRequest req = new FollowingStatusRequest(myUser,randomUser);
        FollowingStatusResponse resp = new FollowsStatusService().getFollowsStatus(req);
        assert(resp.isFollows() == false);
        req = new FollowingStatusRequest(myUser,someoneTheyFollow);
        resp = new FollowsStatusService().getFollowsStatus(req);
        assert(resp.isFollows());
    }

    @Test
    public void manipulateFollow() {
        //these two (manipulate follow and postStatus )do not work ATM due to the nature of the static data
        FollowManipulationRequest req = new FollowManipulationRequest(myUser,someoneTheyFollow,false,"1234");
        FollowManipulationResult resp = new FollowManipulationService().manipulateFollows(req);

        assert(resp.isWasSuccess());

    }

    @Test
    public void postStatus() {


        PostStatusRequest req = new PostStatusRequest(new Status("asdf", (long) 12345678,myUser),"1234");
        PostStatusResponse resp = new PostTweetService().postTweet(req);
        assert(resp.isWasSuccess());

    }

    @Test
    public void registerUser() {
        //register just says it was a success every time due to no back end
        RegisterRequest req = new RegisterRequest("username","password","chase","hiatt",null);
        RegisterResponse resp = new RegisterService().register(req);
        assert(resp.isWasSuccessful());
    }



}