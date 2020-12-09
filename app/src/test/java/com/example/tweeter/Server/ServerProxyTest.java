package com.example.tweeter.Server;

import com.example.tweeter.Server.generators.UserGenerator;
import com.example.tweeter.Server.generators.WordGenerator;
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
import com.example.tweeter.services.AuthTokenHolder;
import com.example.tweeter.services.FeedService;
import com.example.tweeter.services.FollowManipulationService;
import com.example.tweeter.services.FollowerService;
import com.example.tweeter.services.FollowingService;
import com.example.tweeter.services.FollowsStatusService;
import com.example.tweeter.services.LoginService;
import com.example.tweeter.services.ObserverNotificationService;
import com.example.tweeter.services.PostTweetService;
import com.example.tweeter.services.RegisterService;
import com.example.tweeter.services.StoryService;
import com.example.tweeter.services.UserStatsService;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServerProxyTest {
    //these are the integration tests
    ServerProxy proxy = new ServerProxy();
    User myUser = new User("Test","User","Chase","https://s3.amazonaws.com/chasehiattbucket/images/Chase.png");
    User somoneWhoFollowsMe = new User("Bill","Science","bill","https://s3.amazonaws.com/chasehiattbucket/images/bill.png");
    User bimboJoe = new User("Joe","Bimbo","bimboJoe","https://s3.amazonaws.com/chasehiattbucket/images/bimboJoe.png");
    User someoneTheyFollow = new User("Followed","User","Followed","https://s3.amazonaws.com/chasehiattbucket/images/Followed.png");
    User randomUser = new User("Sonia","MCCLAIN","SoniaMCCLAIN685","https://chasehiattbucket.s3.amazonaws.com/images/bill.png");
    User spammable = new User("Story","Man","Story","https://s3.amazonaws.com/chasehiattbucket/images/Story.png");
    User unloved = new User("No","Love","Unloved","https://s3.amazonaws.com/chasehiattbucket/images/Unloved.png");


    String authToken;
    @Before
    public void getAuth(){
        authToken = new LoginService().login(new LoginRequest("username","password")).getAuthToken();
    }

    @Test
    public void getFollowing() {
        FollowingRequest req = new FollowingRequest(myUser,100,null);
        FollowingResponse resp = new FollowingService().getFollowing(req);
        assert(resp.getUsersTheyAreFollowing().contains(someoneTheyFollow));
        assert(resp.getUsersTheyAreFollowing().size() ==1);

        req = new FollowingRequest(somoneWhoFollowsMe,20,null);
        resp = new FollowingService().getFollowing(req);
        assert(resp.getUsersTheyAreFollowing().contains(myUser));

        req = new FollowingRequest(randomUser,20,null);
        resp = new FollowingService().getFollowing(req);
        assert(!resp.getUsersTheyAreFollowing().contains(myUser));


    }

    @Test
    public void getFollowers() {
        FollowerRequest req = new FollowerRequest(myUser,6,null);
        FollowerResponse resp = new FollowerService().getFollowers(req);

        assert(resp.getFollowers().size() == 2);
        for(User u : resp.getFollowers()){
            assert(!u.equals(myUser));
        }
        assert(resp.getFollowers().contains(somoneWhoFollowsMe));

        req = new FollowerRequest(someoneTheyFollow,20,null);
        resp = new FollowerService().getFollowers(req);
        assert(resp.getFollowers().contains(myUser));


    }

    @Test
    public void login() {
        LoginRequest loginRequest = new LoginRequest("Chase","password");
        LoginResponse resp = proxy.login(loginRequest);
        assert(resp.getLoggedInAs().equals(myUser));
        assert(resp.isSuccess());
        assert(resp.getAuthToken() != null);
    }

    @Test
    public void getUserStats() {
        UserStatsRequest req = new UserStatsRequest(myUser, AuthTokenHolder.authToken,myUser);
        UserStatsResponse resp = new UserStatsService().getUserStats(req);
        assert(resp.getNumPeopleTheyAreFollowing() == 1);
       // assert(resp.getNumFollowers() == 5);
        //in this iteration of the fakeData, I have 5 following and followers
    }

    @Test
    public void getStory() {
        StoryRequest req = new StoryRequest(5,myUser,null);
        StoryResponse resp = new StoryService().getStory(req);
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
        FeedRequest req = new FeedRequest(somoneWhoFollowsMe,1,null);
        FeedResponse resp = new FeedService().getFeed(req);
        List<Status> theStatus = resp.getTheStatus();
        assert(theStatus.size() > 0);
        assert(resp.isHasMore());
        assert(theStatus.get(0).getSaidBy().equals(myUser));
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
        String tempAuth = new LoginService().login(new LoginRequest("Unloved","password")).getAuthToken();
        FollowManipulationRequest req = new FollowManipulationRequest(unloved,randomUser,true,tempAuth);
        FollowManipulationResult resp = new FollowManipulationService().manipulateFollows(req);

        assert(resp.isWasSuccess());



    }

    @Test
    public void postStatus() {

        StringBuilder newMessageHolder = new StringBuilder();
        WordGenerator wg = new WordGenerator();
        for(int i=0; i< 5; i++){
            newMessageHolder.append(wg.getWord());
        }


        PostStatusRequest req = new PostStatusRequest(new Status(newMessageHolder.toString(), System.currentTimeMillis()/1000,spammable),new LoginService().login(new LoginRequest("Story","password")).getAuthToken());
        PostStatusResponse resp = new PostTweetService().postTweet(req);
        assert(resp.isWasSuccess());
        StoryRequest storyRequest = new StoryRequest(1,spammable,null);
        StoryResponse storyResponse = new StoryService().getStory(storyRequest);

        assertEquals(storyResponse.getTheStatus().get(0).getSaidBy(), spammable);
        assertEquals(storyResponse.getTheStatus().get(0).getMessage(), newMessageHolder.toString());



    }

    @Test
    public void registerUser() {
        //register just says it was a success every time due to no back end
        User newUser = UserGenerator.getUser();

        RegisterRequest req = new RegisterRequest(newUser.getUserName(),"password",newUser.getFirstName(),newUser.getLastName(),"YXNkZmFzZGZhc2RmYXNkZg==");
        RegisterResponse resp = new RegisterService().register(req);
        assert(resp.isWasSuccessful());
        User expected = new User(newUser.getFirstName(),newUser.getLastName(),newUser.getUserName(),"https://s3.amazonaws.com/chasehiattbucket/images/"+newUser.getUserName()+ ".png");
        LoginResponse loginResp = new LoginService().login(new LoginRequest(newUser.getUserName(),"password"));
        assert(loginResp.isSuccess());
        assert(loginResp.getLoggedInAs().equals(expected));

    }





}